package com.arg.smart.oauth.service.impl;

import com.arg.smart.common.core.exception.MyRuntimeException;
import com.arg.smart.common.core.utils.AuthInfoUtils;
import com.arg.smart.common.core.utils.Utils;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.oauth.api.entity.UserInfo;
import com.arg.smart.common.oauth.api.entity.UserRole;
import com.arg.smart.common.oauth.common.OauthUtils;
import com.arg.smart.oauth.cache.temp.Account2IdTempCache;
import com.arg.smart.oauth.cache.temp.UserPermissionTempCache;
import com.arg.smart.oauth.cache.temp.UserRoleTempCache;
import com.arg.smart.oauth.cache.temp.UserTempCache;
import com.arg.smart.oauth.common.PasswordHelper;
import com.arg.smart.oauth.entity.SsoUser;
import com.arg.smart.oauth.mapper.SsoUserMapper;
import com.arg.smart.oauth.req.ReqSsoUser;
import com.arg.smart.oauth.service.SsoUserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.BeanUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author cgli
 * @date: 2020/2/13 16:51
 */
@Service
@Slf4j
public class SsoUserServiceImpl extends ServiceImpl<SsoUserMapper, SsoUser> implements SsoUserService {
    @Resource
    PasswordHelper passwordHelper;
    @Resource
    UserTempCache userTempCache;
    @Resource
    Account2IdTempCache account2IdTempCache;
    @Resource
    UserRoleTempCache userRoleTempCache;
    @Resource
    UserPermissionTempCache userPermissionTempCache;
    @Resource
    HashedCredentialsMatcher hashedCredentialsMatcher;
    @Resource
    RedisTemplate<String, Object> redisTemplate;

    /**
     * 修改用户密码
     *
     * @param userId
     * @param newPwd
     * @return
     */
    @Override
    public Result<Boolean> changePassword(String userId, String oldPwd, String newPwd) {
        Result<Boolean> result = verifyPassword(newPwd);
        if (!result.isSuccess()) {
            return result;
        }
        SsoUser user = userTempCache.getFromCacheAndDB(userId);
        if (user == null) {
            String error = MessageFormat.format("错误:用户id{0}未获取到用户信息!", userId);
            log.error(error);
            return Result.fail(false, error);
        }
        result = verifyOldPwd(user, oldPwd);
        if (!result.isSuccess()) {
            return result;
        }
        user.setOldPassword(setOldPwd(user.getOldPassword(), user.getPassword()));
        user.setPassword(passwordHelper.encryptPassword(userId, newPwd, user.getSalt()));
        if (user.getOldPassword().indexOf(user.getPassword()) >= 0) {
            return Result.fail(false, "错误:密码5次内不得循环使用");
        }
        if (baseMapper.updateById(user) == 1) {
            //更新用户信息,刷新redis 用户缓存
            userTempCache.updateCacheInfo(user, userId);
            return Result.ok(true, "用户密码-修改密码成功");
        }
        return Result.fail(false, "错误:用户密码-修改密码失败");
    }

    private Result<Boolean> verifyOldPwd(SsoUser ssoUser, String oldPwd) {
        //老密码为null时不校验老密码
        if (StringUtils.isEmpty(oldPwd)) {
            return Result.ok();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                ssoUser.getId(), //用户名
                ssoUser.getPassword(), //密码
                ByteSource.Util.bytes(ssoUser.getId() + ssoUser.getSalt()),
                ""  //调用基类realm
        );
        UsernamePasswordToken token = new UsernamePasswordToken(ssoUser.getAccount(), oldPwd);
        boolean result = hashedCredentialsMatcher.doCredentialsMatch(token, authenticationInfo);
        if (result) {
            return Result.ok(true, "密码校验正确");
        }
        return Result.fail(false, "错误:旧密码校验失败");
    }

    /**
     * 密码校验 密码长度必须8~16位
     * 密码必须由数字、字母、特殊字符组合
     *
     * @param password
     * @return
     */
    private Result<Boolean> verifyPassword(String password) {
        if (StringUtils.isEmpty(password)) {
            return Result.fail(false, "密码不允许为空");
        }
        if (password.length() < 8 || password.length() > 16) {
            return Result.fail(false, "密码长度必须8~16位");
        }
        if (!password.matches("(?=.*\\d)(?=.*[a-zA-Z])(?=.*[^a-zA-Z0-9]).{8,16}")) {
            return Result.fail(false, "密码必须由数字、字母、特殊字符组合");
        }
        return Result.ok(true, "校验成功");
    }

    /**
     * 设置旧密码 将最后一次密码添加到最近密码列表中，密码逗号隔开
     * 只存储最近5次密码
     *
     * @param oldPwd
     * @param pwd
     * @return
     */
    private String setOldPwd(String oldPwd, String pwd) {
        String[] pwds = StringUtils.isEmpty(oldPwd) ? new String[0] : oldPwd.split(",");
        List<String> list = new ArrayList<>(Arrays.asList(pwds));
        if (list.size() >= 5) {
            list.remove(0);
        }
        if (!StringUtils.isEmpty(pwd)) {
            list.add(pwd);
        }
        return StringUtils.join(list.iterator(), ",");
    }

    @Override
    @Transactional
    public Result<SsoUser> insertUser(SsoUser user, String clientId) {
        return insertUserInfo(user, clientId);
    }

    @Override
    @Transactional
    public Result<SsoUser> insertUser(SsoUser user) {
        return insertUserInfo(user, AuthInfoUtils.getCurrentClientId());
    }

    private Result<SsoUser> insertUserInfo(SsoUser user, String clientId) {
        validateUser(user, "新增");
        //如果外部已经赋值过id就不重新赋值了
        if (StringUtils.isEmpty(user.getId())) {
            user.setId(Utils.uuid32());
        }
        //如果外部已经设置过盐，此处不设置
        if (StringUtils.isEmpty(user.getSalt())) {
            user.setSalt(PasswordHelper.buildSalt());
        }
        //短信直接创建用户可以初始不设置密码此处密码允许为空
        if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(passwordHelper.encryptPassword(user.getId(), user.getPassword(), user.getSalt()));
        }
        if (null == user.getStatus()) {
            user.setStatus(0);
        }
        if (null == user.getDelFlag()) {
            user.setDelFlag(0);
        }
        int res = baseMapper.insert(user);
        if (res > 0) {
            insertUserClient(user.getId(), clientId);
            insertUserOrg(user.getId(), user.getOrgId());
            insertUserRole(user.getId(), user.getRoleIds());
            userTempCache.updateCacheInfo(user, user.getId());
            return Result.ok(user, "用户信息-新增成功");
        }
        throw new MyRuntimeException("错误:用户信息-新增失败!");
    }

    @Override
    @Transactional
    public Result<SsoUser> updateUser(SsoUser user) {
        validateUser(user, "修改");
        //获取用户信息
        SsoUser ssoUser = this.getUserById(user.getId());
        //帐号名称密码不在此处更新除非账号为空
        String account = user.getAccount();
        if(ssoUser.getAccount() != null){
            user.setAccount(null);
        }
        user.setPassword(null);
        int res = baseMapper.updateById(user);
        if (res > 0) {
            user.setAccount(account);
            if (null != user.getOrgId()) {
                baseMapper.deleteUserOrg(user.getId());
                insertUserOrg(user.getId(), user.getOrgId());
            }
            if (null != user.getRoleIds()) {
                baseMapper.deleteUserRole(user.getId());
                insertUserRole(user.getId(), user.getRoleIds());
            }
            String clientId = AuthInfoUtils.getCurrentClientId();
            //移除缓存下次登录时会自动拉取
            CompletableFuture.runAsync(() -> removeUserCache(user.getId(), clientId));
            return Result.ok(user, "用户信息-更新成功");
        }
        throw new MyRuntimeException("错误:未找到用户信息更新数据");
    }

    /**
     * 校验用户帐号 账号，手机，email均不允许重复
     *
     * @param user
     * @return
     */
    private boolean validateUser(SsoUser user, String operate) {
        if (isAccountExist(user.getAccount(), user.getId())) {
            throw new MyRuntimeException("错误:帐号已存在-" + operate + "失败!");
        }
        if (isAccountExist(user.getPhone(), user.getId())) {
            throw new MyRuntimeException("错误:手机号已存在-" + operate + "失败!");
        }
        if (isAccountExist(user.getEmail(), user.getId())) {
            throw new MyRuntimeException("错误:email已存在-" + operate + "失败!");
        }
        if (!AuthInfoUtils.isSuper(user.getId()) && AuthInfoUtils.isContainSuperAdmin(user.getRoleIds())) {
            throw new MyRuntimeException("错误:不允许设置为超户!");
        }
        if (user.getAccount() != null) {
            if (user.getAccount().length() > 30) {
                throw new MyRuntimeException("错误:帐号字符不要超过30个字符");
            }
            Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
            Matcher matcher = pattern.matcher(user.getAccount());
            if (!matcher.matches()) {
                throw new MyRuntimeException("错误:帐号必须只允许为数字和字母");
            }
        }
        if (!StringUtils.isEmpty(user.getPhone())) {
            Pattern pattern = Pattern.compile("^1[3-9][0-9]\\d{8}$");
            Matcher matcher = pattern.matcher(user.getPhone());
            if (!matcher.matches()) {
                throw new MyRuntimeException("错误:手机号不正确");
            }
        }
        return true;
    }

    @Override
    public boolean removeUser(String id) {
        SsoUser ssoUser = new SsoUser();
        ssoUser.setDelFlag(1).setId(id);
        if (baseMapper.updateById(ssoUser) == 1) {
            String clientId = AuthInfoUtils.getCurrentClientId();
            CompletableFuture.runAsync(() -> removeUserCache(id, clientId));
            log.info(MessageFormat.format("删除用户成功,用户ID:{0}", id));
            return true;
        }
        log.error(MessageFormat.format("删除用户失败,用户ID:{0}", id));
        return false;
    }

    /**
     * 移除用户相关缓存
     *
     * @param userId
     */
    private void removeUserCache(String userId, String clientId) {
        userTempCache.removeOneCache(userId);
        userRoleTempCache.removeOneCache(userId, clientId);
        userPermissionTempCache.removeOneCache(userId, clientId);
    }

    @Override
    public SsoUser getUserByAccount(String account) {
        QueryWrapper<SsoUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("account", account);
        return this.getOne(queryWrapper);
    }

    @Override
    public SsoUser getUserById(String userId) {
        return userTempCache.getFromCacheAndDB(userId);
    }

    public UserInfo getUserByIdNoPwd(String userId) {
        SsoUser ssoUser = userTempCache.getFromCacheAndDB(userId);
        if(ssoUser == null){
            return null;
        }
        UserInfo userInfo = new UserInfo();
        BeanUtils.copyProperties(ssoUser, userInfo);
        return userInfo;
    }

    @Override
    public List<UserInfo> getUserList(ReqSsoUser reqSsoUser) {
        return baseMapper.getUserList(reqSsoUser);
    }

    @Override
    public List<UserRole> getUserRoles(String userId, String clientId) {
        return userRoleTempCache.getFromCacheAndDB(userId, clientId);
    }

    @Override
    public Set<String> getUserPermissions(String userId, String clientId) {
        return userPermissionTempCache.getFromCacheAndDB(userId, clientId);
    }

    /**
     * 获取用户客户端是否存在
     *
     * @param userId
     * @param clientId
     * @return
     */
    @Override
    public boolean isUserClientExist(String userId, String clientId) {
        return baseMapper.isUserClientExist(userId, clientId) > 0;
    }

    @Override
    public boolean isAccountExist(String account, String userId) {
        return baseMapper.isAccountExist(account, userId) > 0;
    }

    @Override
    public int insertUserRole(String userId, List<String> roles) {
        if (roles == null || roles.size() == 0) {
            return 0;
        }
        int count = baseMapper.insertUserRole(userId, roles);
        if (count > 0) {
            return count;
        }
        throw new MyRuntimeException("错误:插入用户角色失败");
    }

    @Override
    public int insertUserOrg(String userId, String orgList) {
        //todo 暂时只支持一个用户挂在一个组织下，后期根据情况完善是否一个用户挂在多个组织下
        if (StringUtils.isEmpty(orgList)) {
            return 0;
        }
        int count = baseMapper.insertUserOrg(userId, Arrays.asList(new String[]{orgList}));
        if (count > 0) {
            return count;
        }
        throw new MyRuntimeException("错误:插入用户组织失败");
    }

    public int insertUserClient(String userId, String clientId) {
        if (StringUtils.isEmpty(clientId)) {
            throw new MyRuntimeException("错误:客户端ID不允许为空");
        }
        int count = baseMapper.insertUserClient(userId, Arrays.asList(new String[]{clientId}));
        if (count > 0) {
            return count;
        }
        throw new MyRuntimeException("错误:插入用户所属客户端失败");
    }

    @Override
    public SsoUser getUserByOpenId(String openid) {
        LambdaQueryWrapper<SsoUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SsoUser::getOpenid,openid);
        queryWrapper.eq(SsoUser::getDelFlag,0);
        return this.getOne(queryWrapper);
    }

    @Override
    public SsoUser getUserByPhone(String phone) {
        LambdaQueryWrapper<SsoUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SsoUser::getPhone,phone);
        queryWrapper.eq(SsoUser::getDelFlag,0);
        return this.getOne(queryWrapper);
    }

    @Override
    public Boolean hasPassword(String userId) {
        SsoUser user = this.getUserById(userId);
        return user.getPassword() != null;
    }

    @Override
    public Integer getUserCount() {
        log.info("service：getUserCount");
       return baseMapper.getUserCount();
    }
}

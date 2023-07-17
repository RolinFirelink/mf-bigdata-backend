package com.arg.smart.oauth.service;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.oauth.api.entity.UserInfo;
import com.arg.smart.common.oauth.api.entity.UserRole;
import com.arg.smart.oauth.entity.SsoUser;
import com.arg.smart.oauth.req.ReqSsoUser;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Set;

/**
 * @author cgli
 * @date: 2020/2/13 16:50
 */
public interface SsoUserService extends IService<SsoUser> {
    Result<Boolean> changePassword(String userId, String oldPwd, String newPwd);

    Result<SsoUser> insertUser(SsoUser user, String clientId);

    Result<SsoUser> insertUser(SsoUser user);

    Result<SsoUser> updateUser(SsoUser user);

    boolean removeUser(String id);

    SsoUser getUserByAccount(String account);

    SsoUser getUserById(String userId);

    UserInfo getUserByIdNoPwd(String userId);

    List<UserInfo> getUserList(ReqSsoUser reqSsoUser);

    List<UserRole> getUserRoles(String userId, String clientId);

    /**
     * 通过用户ID获取按钮权限
     *
     * @param userId
     * @param clientId
     * @return
     */
    Set<String> getUserPermissions(String userId, String clientId);

    /**
     * 判断客户端下是否存在该用户
     *
     * @param account
     * @param clientId
     * @return
     */
    boolean isUserClientExist(String account, String clientId);

    /**
     * 判断帐号是否存在
     *
     * @param account 帐号
     * @param userId  如果存在userId，排除当前userId
     * @return
     */
    boolean isAccountExist(String account, String userId);

    /**
     * 插入用户角色关系
     *
     * @param userId
     * @param roles
     * @return
     */
    int insertUserRole(String userId, List<String> roles);

    /**
     * 插入用户组织关系
     *
     * @param userId
     * @param orgList
     * @return
     */
    int insertUserOrg(String userId, String orgList);

    /**
     * 插入用户所属客户端
     *
     * @param userId
     * @param clientId
     * @return
     */
    int insertUserClient(String userId, String clientId);


    SsoUser getUserByOpenId(String openid);
}

package com.arg.smart.oauth.service.impl;

import com.arg.smart.common.core.constants.RPCConstants;
import com.arg.smart.common.core.exception.CaptchaException;
import com.arg.smart.common.core.utils.AuthInfoUtils;
import com.arg.smart.common.core.utils.ServletUtils;
import com.arg.smart.common.core.utils.Utils;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.oauth.common.SerConstant;
import com.arg.smart.common.oauth.entity.RedisAccessToken;
import com.arg.smart.common.redis.common.RedisPrefix;
import com.arg.smart.oauth.common.MyUsernamePasswordToken;
import com.arg.smart.oauth.entity.SsoUser;
import com.arg.smart.oauth.service.LoginService;
import com.arg.smart.oauth.service.SsoUserService;
import com.arg.smart.oauth.validator.GetCodeValidator;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.support.atomic.RedisAtomicLong;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.MessageFormat;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author cgli
 * @date: 2020/2/15 16:10
 */
@Service
@Slf4j
public class LoginServiceImpl implements LoginService {
    @Resource
    SsoUserService ssoUserService;
    @Resource
    RedisTemplate<String, Object> redisTemplate;
    @Resource
    GetCodeValidator getCodeValidator;
    //允许连续出错时间间隔的最大错误数
    final static int ERROR_COUNT = 5;
    //允许连续出错的时间间隔 单位:分钟  30分钟内不允许连续出错5次
    final static long ERROR_TIME_INTERVAL = 30;

    @Override
    public boolean getLogin(Model model, HttpServletRequest request) {
        validateCode(model, request);
        return false;
    }

    @Override
    public boolean postLogin(Model model, HttpServletRequest request) {
        String captchaEx = request.getHeader(RPCConstants.REQ_CHECK_CAPTCHA_EXCEPTION);
        if (!StringUtils.isEmpty(captchaEx)) {
            model.addAttribute(SerConstant.ERROR_MSG, CaptchaException.Info.getExceptionInfo(captchaEx).toString());
            return false;
        }
        if (!validateCode(model, request)) {
            return false;
        }
        return login(model, request);
    }

    /**
     * 请求code参数校验
     *
     * @param model
     * @param request
     * @return
     */
    private boolean validateCode(Model model, HttpServletRequest request) {
        Result result = getCodeValidator.validateClient(request, null);
        if (!result.isSuccess()) {
            model.addAttribute(SerConstant.ERROR_MSG, result.getMsg());
            return false;
        }
        return true;
    }

    /**
     * web请求登录 构建model返回值
     *
     * @param model
     * @param request
     * @return
     */
    public boolean login(Model model, HttpServletRequest request) {
        Result<String> result = login(request);
        for (Map.Entry<String, String> entry : result.getParam().entrySet()) {
            model.addAttribute(entry.getKey(), entry.getValue());
        }
        return result.isSuccess();
    }

    /**
     * 登录用户验证逻辑
     *
     * @param request
     * @return
     */
    @Override
    public Result<String> login(HttpServletRequest request) {
        String username = request.getParameter(OAuth.OAUTH_USERNAME);
        String password = request.getParameter(OAuth.OAUTH_PASSWORD);
        SerConstant.LoginType loginType = SerConstant.LoginType.getLoginType(request.getParameter(SerConstant.LOGIN_TYPE));
        String rememberMe = request.getParameter(SerConstant.REMEMBER_ME);
        String clientId = request.getParameter(SerConstant.CLIENT_ID);
        return login(username, password, loginType, clientId, rememberMe);

    }


    /**
     * 登录用户验证逻辑
     *
     * @param username
     * @param password
     * @param loginType
     * @return
     */
    public Result<String> login(String username, String password, SerConstant.LoginType loginType, String clientId, String rememberMe) {
        boolean remember = false;
        if (!StringUtils.isEmpty(rememberMe)) {
            remember = Boolean.parseBoolean(rememberMe);
        }
        Result<String> result = new Result<>();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            result.setSuccess(false).setMsg(SerConstant.INVALID_USER_SECRET_DESCRIPTION)
                    .getParam().put(SerConstant.ERROR_MSG, SerConstant.INVALID_USER_SECRET_DESCRIPTION);
            return result;
        }
        MyUsernamePasswordToken token = new MyUsernamePasswordToken(username, password, remember)
                .setLoginType(loginType).setClientId(clientId);
        try {
            SecurityUtils.getSubject().login(token);
            return result;
        } catch (IncorrectCredentialsException ex) {
            //错误凭证错误信息
            result.setSuccess(false).setMsg(ex.getMessage()).getParam().put(SerConstant.ERROR_MSG, ex.getMessage());
            log.info("用户:" + username + "登录客户端:" + "" + "失败" + ex.getMessage());
            return result;
        } catch (Exception ex) {
            //其他异常错误信息
            result.setSuccess(false).setMsg(ex.getMessage()).getParam().put(SerConstant.ERROR_MSG, ex.getMessage());
            log.info("用户:" + username + "登录客户端:" + "" + "失败" + ex.getMessage());
            return result;
        } finally {
            result.setData(token.getUserInfo() != null ? token.getUserInfo().getId() : null);
            result.getParam().put(OAuth.OAUTH_USERNAME, username);
            result.getParam().put(SerConstant.LOGIN_TYPE, loginType.toString());
        }
    }

    @Override
    public boolean retryLimit(String userId, boolean matches) {
        SsoUser user = ssoUserService.getUserById(userId);
        if (user == null) {
            log.error(userId + SerConstant.INVALID_USER_ID_DESCRIPTION);
            throw new IncorrectCredentialsException(SerConstant.INVALID_USER_ID_DESCRIPTION);
        }
        //超户不允许禁用、锁定、删除
        if (!AuthInfoUtils.isSuper(userId)) {
            if (SerConstant.AccountState.禁用.getValue() == user.getStatus()) {
                log.error(userId + SerConstant.ACCOUNT_DISABLE_DESCRIPTION);
                throw new IncorrectCredentialsException(SerConstant.ACCOUNT_DISABLE_DESCRIPTION);
            }
            if (SerConstant.AccountState.锁定.getValue() == user.getStatus()) {
                log.error(userId + SerConstant.ACCOUNT_LOCK_DESCRIPTION);
                throw new IncorrectCredentialsException(SerConstant.ACCOUNT_LOCK_DESCRIPTION);
            }
            if (user.getDelFlag().equals(1)) {
                log.error(userId + SerConstant.ACCOUNT_DELETE_DESCRIPTION);
                throw new IncorrectCredentialsException(SerConstant.ACCOUNT_DELETE_DESCRIPTION);
            }
        }
        int count = getLoginCount(userId);
        if (matches) {
            //清空重试次数
            removeLoginCount(userId);
            return true;
        }
        if (count >= ERROR_COUNT) {
            String error = MessageFormat.format("{0},连续输错5次密码，账号锁定"
                    , SerConstant.INVALID_USER_SECRET_DESCRIPTION);
            user.setStatus(1);
            ssoUserService.updateUser(user);
            log.error(userId + error);
            //规定时间内重试ERROR_COUNT次，抛出多次尝试异常
            throw new ExcessiveAttemptsException(error);
        }
        String error = MessageFormat.format("{0},连续出错{1}次,错误{2}次将被锁定"
                , SerConstant.INVALID_USER_SECRET_DESCRIPTION, count, ERROR_COUNT);
        log.error(userId + error);
        throw new IncorrectCredentialsException(error);
    }

    @Override
    public void sendMsg(String phone, String msg) {
        //TODO 根据具体短信网关实现

    }

    @Override
    public void saveSmsCode(String phone, String code) {
        redisTemplate.opsForValue().set(RedisPrefix.buildSMSCodeKey(phone), code, 5, TimeUnit.MINUTES);
    }

    @Override
    public void delSmsCode(String phone) {
        redisTemplate.delete(RedisPrefix.buildSMSCodeKey(phone));
    }

    @Override
    public String getSmsCode(String phone) {
        return (String) redisTemplate.opsForValue().get(RedisPrefix.buildSMSCodeKey(phone));
    }

    @Override
    public void saveSmsCodeTime(String phone) {
        redisTemplate.opsForValue().set(RedisPrefix.buildSMSCodeTimeKey(phone), "", 1, TimeUnit.MINUTES);
    }

    @Override
    public void delSmsCodeTime(String phone) {
        redisTemplate.delete(RedisPrefix.buildSMSCodeTimeKey(phone));
    }

    @Override
    public long getSmsCodeTime(String phone) {
        return redisTemplate.getExpire(RedisPrefix.buildSMSCodeTimeKey(phone));
    }

    @Override
    public void sessionKeyTempCache(String sessionKey, String openId) {
        redisTemplate.opsForValue().set(RedisPrefix.buildSessionKey(sessionKey), openId, 5, TimeUnit.MINUTES);
    }

    @Override
    public String getOpenIdBySessionKey(String sessionKey) {
        return (String) redisTemplate.opsForValue().get(RedisPrefix.buildSessionKey(sessionKey));
    }

    /**
     * 获取30分钟内登录次数
     *
     * @param userId
     * @return
     */
    public int getLoginCount(String userId) {
        RedisAtomicLong ral = new RedisAtomicLong(RedisPrefix.buildLoginCountKey(userId)
                , redisTemplate.getConnectionFactory());
        ral.incrementAndGet();
        //第一次设置允许错误的时间间隔
        if (ral.intValue() == 1) {
            ral.expire(ERROR_TIME_INTERVAL, TimeUnit.MINUTES);
        }
        return ral.intValue();
    }

    /**
     * 移除登录次数
     *
     * @param userId
     */
    public void removeLoginCount(String userId) {
        redisTemplate.delete(RedisPrefix.buildLoginCountKey(userId));
    }
}

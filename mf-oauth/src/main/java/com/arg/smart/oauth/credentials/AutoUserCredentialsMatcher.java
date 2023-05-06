package com.arg.smart.oauth.credentials;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.oauth.common.SerConstant;
import com.arg.smart.common.core.exception.OAuthValidateException;
import com.arg.smart.oauth.entity.SsoUser;
import com.arg.smart.oauth.service.SsoUserService;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import javax.annotation.Resource;

/**
 * 不存在的用户自动创建用户
 *
 * @author cgli
 * @date: 2021/10/26 17:57
 */
public class AutoUserCredentialsMatcher extends SimpleCredentialsMatcher {
    @Resource
    SsoUserService ssoUserService;

    protected void insertNewUser(boolean newUser, SsoUser user, String clientId) {
        if (newUser) {
            Result<SsoUser> result = ssoUserService.insertUser(user, clientId);
            if (!result.isSuccess()) {
                throw new OAuthValidateException(SerConstant.INVALID_NEW_USER_DESCRIPTION);
            }
        }
    }
}

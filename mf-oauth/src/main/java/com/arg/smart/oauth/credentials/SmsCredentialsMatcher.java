package com.arg.smart.oauth.credentials;

import com.arg.smart.oauth.common.MyUsernamePasswordToken;
import com.arg.smart.oauth.service.LoginService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;

import javax.annotation.Resource;


/**
 * @author cgli
 * @date: 2020/2/25 16:51
 */
public class SmsCredentialsMatcher extends AutoUserCredentialsMatcher {
    @Resource
    LoginService loginService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        MyUsernamePasswordToken myToken = (MyUsernamePasswordToken) authenticationToken;
        boolean matches = super.doCredentialsMatch(authenticationToken, authenticationInfo);
        if (matches) {
            insertNewUser(myToken.isNew(), myToken.getUserInfo(), myToken.getClientId());
        }
        boolean success = loginService.retryLimit(myToken.getUserInfo().getId(), matches);
        if (success) {
            loginService.delSmsCode(myToken.getUsername());
        }
        return success;
    }
}

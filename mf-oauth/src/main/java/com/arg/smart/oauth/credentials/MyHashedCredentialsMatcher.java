package com.arg.smart.oauth.credentials;

import com.arg.smart.oauth.common.MyUsernamePasswordToken;
import com.arg.smart.oauth.service.LoginService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import javax.annotation.Resource;

/**
 * @author cgli
 * @date: 2020/2/10 19:48
 */
public class MyHashedCredentialsMatcher extends HashedCredentialsMatcher {
    @Resource
    LoginService loginService;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        MyUsernamePasswordToken myToken = (MyUsernamePasswordToken) token;
        boolean matches = super.doCredentialsMatch(token, info);
        return loginService.retryLimit(myToken.getUserInfo().getId(), matches);
    }

}

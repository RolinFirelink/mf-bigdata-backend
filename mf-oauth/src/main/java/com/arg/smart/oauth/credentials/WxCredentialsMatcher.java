package com.arg.smart.oauth.credentials;

import com.arg.smart.oauth.common.MyUsernamePasswordToken;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author cgli
 * @date: 2021/10/26 17:00
 */
public class WxCredentialsMatcher extends AutoUserCredentialsMatcher {
    @Override
    public boolean doCredentialsMatch(AuthenticationToken authenticationToken, AuthenticationInfo authenticationInfo) {
        MyUsernamePasswordToken myToken = (MyUsernamePasswordToken) authenticationToken;
        insertNewUser(myToken.isNew(), myToken.getUserInfo(), myToken.getClientId());
        return true;
    }
}

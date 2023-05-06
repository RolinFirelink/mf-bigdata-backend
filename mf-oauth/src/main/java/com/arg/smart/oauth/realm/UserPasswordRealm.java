package com.arg.smart.oauth.realm;

import com.arg.smart.oauth.entity.SsoUser;
import org.apache.shiro.authc.*;
import org.apache.shiro.util.ByteSource;

/**
 * @author cgli
 * @date: 2020/2/11 9:33
 */
public class UserPasswordRealm extends BaseRealm {

    @Override
    protected AuthenticationInfo buildAuthenticationInfo(SsoUser user, AuthenticationToken authenticationToken, boolean newUser) {
        //交给AuthenticatingRealm使用CredentialsMatcher进行密码匹配，如果觉得人家的不好可以自定义实现
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                user.getId(), //用户名
                user.getPassword(), //密码
                ByteSource.Util.bytes(user.getId() + user.getSalt()),
                getName()  //调用基类realm
        );
        return authenticationInfo;
    }
}

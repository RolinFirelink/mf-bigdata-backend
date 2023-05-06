package com.arg.smart.oauth.realm;

import com.arg.smart.common.oauth.common.SerConstant;
import com.arg.smart.oauth.entity.SsoUser;
import com.arg.smart.oauth.service.LoginService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;

import javax.annotation.Resource;


/**
 * @author cgli
 * @date: 2020/2/11 9:41
 */
public class PhoneSmsRealm extends BaseRealm {
    @Resource
    LoginService loginService;

    @Override
    protected AuthenticationInfo buildAuthenticationInfo(SsoUser user, AuthenticationToken authenticationToken, boolean newUser) {
        String code = loginService.getSmsCode(user.getPhone());
        if (StringUtils.isEmpty(code)) {
            throw new IncorrectCredentialsException(SerConstant.INVALID_PHONE_CODE_DESCRIPTION);
        }
        AuthenticationInfo authorizationInfo = new SimpleAuthenticationInfo(
                user.getId(), code, getName());
        return authorizationInfo;
    }
}

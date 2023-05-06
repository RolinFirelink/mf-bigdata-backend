package com.arg.smart.oauth.common;

import com.arg.smart.common.oauth.common.SerConstant;
import com.arg.smart.oauth.entity.SsoUser;
import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author cgli
 * @date: 2020/2/10 19:28
 */
public class MyUsernamePasswordToken extends UsernamePasswordToken {

    //用户ID
    private SsoUser userInfo;
    //是否新用户
    private boolean isNew;

    //登录类型
    private SerConstant.LoginType loginType = SerConstant.LoginType.密码登录;

    //客户端ID
    private String clientId;

    public MyUsernamePasswordToken(String username, String password) {
        super(username, password);
    }

    public MyUsernamePasswordToken(String username, String password, boolean rememberMe) {
        super(username, password, rememberMe);
    }


    public SerConstant.LoginType getLoginType() {
        return loginType;
    }

    public MyUsernamePasswordToken setLoginType(SerConstant.LoginType loginType) {
        this.loginType = loginType;
        return this;
    }

    public SsoUser getUserInfo() {
        return userInfo;
    }

    public MyUsernamePasswordToken setUserInfo(SsoUser userInfo) {
        this.userInfo = userInfo;
        return this;
    }

    public boolean isNew() {
        return isNew;
    }

    public MyUsernamePasswordToken setNew(boolean aNew) {
        isNew = aNew;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public MyUsernamePasswordToken setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

}

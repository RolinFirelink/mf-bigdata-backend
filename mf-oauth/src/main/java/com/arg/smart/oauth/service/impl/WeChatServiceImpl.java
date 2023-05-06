package com.arg.smart.oauth.service.impl;

import com.arg.smart.common.core.utils.ServletUtils;
import com.arg.smart.common.core.utils.Utils;
import com.arg.smart.common.oauth.common.SerConstant;
import com.arg.smart.common.oauth.entity.AccessToken;
import com.arg.smart.common.oauth.entity.WeChatToken;
import com.arg.smart.common.oauth.service.impl.WeChatTokenServiceImpl;
import com.arg.smart.oauth.cache.temp.OpenIdTempCache;
import com.arg.smart.oauth.entity.SsoUser;
import com.arg.smart.oauth.service.SsoUserService;
import com.arg.smart.oauth.service.WeChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author cgli
 * @description: 微信接口服务实现
 * @date: 2021/12/14 9:40
 */
@Service
@Slf4j
public class WeChatServiceImpl implements WeChatService {
    @Value("${oauth2.expire.token}")
    private long tokenExpire = 21600;
    @Value("${oauth2.expire.refreshToken}")
    private long reTokenExpire = 604800;

    @Resource
    OpenIdTempCache openIdTempCache;
    @Resource
    SsoUserService ssoUserService;
    @Resource
    WeChatTokenServiceImpl weChatTokenService;


    @Override
    public String getUserIdByOpenId(String openId) {
        return openIdTempCache.getFromCacheAndDB(openId);
    }

    @Override
    public boolean bindWeChat(String openId, String userId) {
        SsoUser user = new SsoUser();
        user.setOpenid(openId);
        user.setId(userId);
        return ssoUserService.updateById(user);
    }

    @Override
    public boolean bindWeChat(String openId, String userId, String nickname) {
        return false;
    }

    @Override
    public WeChatToken buildWeChatToken(String openId, String sessionKey, String userId) {
        WeChatToken weChatToken = new WeChatToken();
        weChatToken.setOpenid(openId);
        weChatToken.setSession_key(sessionKey);
        weChatToken.setAccess_token(SerConstant.WX_PREFIX + Utils.uuid32());
        weChatToken.setRefresh_token(SerConstant.WX_PREFIX + Utils.uuid32());
        weChatToken.setUserId(userId);
        SsoUser user = ssoUserService.getUserById(userId);
        weChatToken.setAccount(user.getAccount());
        weChatToken.setExpires_in(tokenExpire);
        weChatToken.setReTokenExpire(reTokenExpire);
        weChatToken.setIp(Utils.getRemoteIP(ServletUtils.getRequest()));
        weChatTokenService.setToken(weChatToken);
        weChatTokenService.setRefreshToken(weChatToken);
        return weChatToken;
    }

    @Override
    public AccessToken convertToken(WeChatToken weChatToken) {
        //重新copy屏蔽不像外返回的属性
        return new AccessToken().setAccess_token(weChatToken.getAccess_token())
                .setRefresh_token(weChatToken.getRefresh_token())
                .setExpires_in(weChatToken.getExpires_in());
    }
}
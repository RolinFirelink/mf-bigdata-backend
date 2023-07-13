package com.arg.smart.oauth.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.oauth.api.entity.UserInfo;
import com.arg.smart.common.oauth.api.vo.UserInfoVo;
import com.arg.smart.common.oauth.entity.AuthorizationCode;
import com.arg.smart.common.oauth.entity.RedisAccessToken;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.oauth.entity.OnlineUser;
import org.apache.oltu.oauth2.as.request.OAuthAuthzRequest;
import org.apache.oltu.oauth2.as.request.OAuthTokenRequest;

/**
 * @author cgli
 * @date: 2020/2/13 12:51
 */
public interface OAuth2Service {
    AuthorizationCode buildCode(OAuthAuthzRequest request);

    void setCode(AuthorizationCode code);

    void delCode(String code);

    AuthorizationCode getCode(String code);

    RedisAccessToken buildToken(OAuthTokenRequest request);
    RedisAccessToken code2Token(OAuthTokenRequest request, AuthorizationCode code);

    RedisAccessToken refresh2Token(RedisAccessToken token);

    UserInfo getUserInfo(String userId);

    UserInfoVo getUserInfoAndRoles(String userId, String clientId);

    String getCurrentUser();

    /**
     * 获取在线用户
     *
     * @return
     */
    PageResult<OnlineUser> getOnlineUser(ReqPage reqPage);

    /**
     * 解密token
     *
     * @param token
     * @return
     */
    String decryptToken(String token);

}

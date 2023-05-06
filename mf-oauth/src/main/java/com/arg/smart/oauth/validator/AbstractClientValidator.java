package com.arg.smart.oauth.validator;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.oauth.validator.IBaseValidator;
import com.arg.smart.oauth.cache.temp.ClientTempCache;
import com.arg.smart.oauth.entity.OAuthClient;
import org.apache.oltu.oauth2.common.OAuth;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author cgli
 * @date: 2020/2/16 18:10
 */
public abstract class AbstractClientValidator implements IBaseValidator<OAuthClient> {

    @Resource
    ClientTempCache clientTempCache;

    /**
     * 获取客户端信息
     * 如果参数中已传入客户端信息直接返回，如果未传入从redis或数据库中获取
     *
     * @param request
     * @param result
     * @return
     */
    public Result<OAuthClient> getOAuthClient(HttpServletRequest request, Result<OAuthClient> result) {
        OAuthClient client;
        if (result == null || result.getData() == null) {
            result = new Result<>();
            String clientId = request.getParameter(OAuth.OAUTH_CLIENT_ID);
            client = clientTempCache.getFromCacheAndDB(clientId);
            if (client == null) {
                return result.setSuccess(false).setMsg("错误:校验客户端ID失败!");
            }
            result.setData(client);
        }
        return result;
    }
}

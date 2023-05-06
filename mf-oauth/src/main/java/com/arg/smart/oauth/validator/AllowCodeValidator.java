package com.arg.smart.oauth.validator;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.oauth.entity.OAuthClient;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cgli
 * @date: 2020/2/16 18:57
 */
@Component
public class AllowCodeValidator extends AbstractClientValidator {
    @Override
    public Result<OAuthClient> validate(HttpServletRequest request, Result<OAuthClient> result) {
        Result<OAuthClient> result1 = getOAuthClient(request, result);
        if (!result1.isSuccess()) {
            return result1;
        }
        if(result1.getData().getAuthorizedGrantTypes().indexOf("authorization_code")<0){
            return result1.setSuccess(false).setMsg("错误:该客户端不支持code请求方式！");
        }
        return result1;
    }
}

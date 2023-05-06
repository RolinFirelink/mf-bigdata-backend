package com.arg.smart.oauth.validator;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.oauth.entity.OAuthClient;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cgli
 * @date: 2020/2/13 13:58
 */
@Component
public class ClientIdExistValidator extends AbstractClientValidator {

    @Override
    public Result<OAuthClient> validate(HttpServletRequest request, Result<OAuthClient> result) {
        return getOAuthClient(request, result);
    }
}

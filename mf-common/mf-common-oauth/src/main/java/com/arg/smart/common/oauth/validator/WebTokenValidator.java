package com.arg.smart.common.oauth.validator;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.oauth.entity.RedisAccessToken;
import com.arg.smart.common.oauth.service.impl.WebTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cgli
 * @date: 2020/2/17 19:06
 */
@Component
public class WebTokenValidator extends AbstractTokenValidator<RedisAccessToken> {

    public WebTokenValidator(@Autowired WebTokenServiceImpl webTokenService) {
        super(webTokenService);
    }

    public Result<RedisAccessToken> validate(ServerHttpRequest request) {
        return validateT(request, null);
    }

    public Result<RedisAccessToken> validate(HttpServletRequest request) {
        return validateT(request, null);
    }

    @Override
    public Result<RedisAccessToken> validate(HttpServletRequest request, Result<RedisAccessToken> result) {
        return validateT(request, result);
    }

}

package com.arg.smart.common.oauth.validator;


import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.oauth.entity.WeChatToken;
import com.arg.smart.common.oauth.service.impl.WeChatTokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author cgli
 * @description: 微信请求token校验
 * @date: 2021/12/14 9:33
 */
@Component
public class WeChatTokenValidator extends AbstractTokenValidator<WeChatToken> {

    public WeChatTokenValidator(@Autowired WeChatTokenServiceImpl weChatTokenService) {
        super(weChatTokenService);
    }

    public Result<WeChatToken> validate(HttpServletRequest request) {
        return validateT(request, null);
    }

    @Override
    public Result<WeChatToken> validate(HttpServletRequest request, Result<WeChatToken> result) {
        return validateT(request, result);
    }
}

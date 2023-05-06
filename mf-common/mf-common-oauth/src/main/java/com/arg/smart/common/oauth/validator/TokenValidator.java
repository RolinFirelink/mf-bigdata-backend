package com.arg.smart.common.oauth.validator;

import com.arg.smart.common.core.utils.AuthInfoUtils;
import com.arg.smart.common.core.utils.StringUtils;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.oauth.common.SerConstant;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @description: 统一token校验
 * @author cgli
 * @date: 2023/1/29 23:28
 */
@Component
public class TokenValidator {
    @Resource
    private WebTokenValidator webTokenValidator;
    @Resource
    private WeChatTokenValidator weChatTokenValidator;

    public <R> Result<?> validator(R request) {
        String accessToken = AuthInfoUtils.getAccessToken(request);
        if (StringUtils.isEmpty(accessToken)) {
            return Result.fail("错误:令牌token不允许为空");
        }
        Result<?> result;
        if (accessToken.startsWith(SerConstant.WX_PREFIX)) {
            result = weChatTokenValidator.validate(accessToken);
        } else {
            result = webTokenValidator.validate(accessToken);
        }
        return result;
    }
}

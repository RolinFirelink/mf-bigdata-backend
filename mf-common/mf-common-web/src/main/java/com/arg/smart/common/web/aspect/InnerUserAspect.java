package com.arg.smart.common.web.aspect;

import com.arg.smart.common.core.annotation.GlobalException;
import com.arg.smart.common.core.constants.RPCConstants;
import com.arg.smart.common.core.exception.OAuthValidateException;
import com.arg.smart.common.core.utils.ServletUtils;
import com.arg.smart.common.core.utils.StringUtils;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.oauth.validator.TokenValidator;
import com.arg.smart.common.web.annotation.InnerUser;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


/**
 * @author cgli
 * @description: 内部用户校验切面
 * @date: 2021/12/3 20:28
 */
@Aspect
@Component
@GlobalException
public class InnerUserAspect {
    @Resource
    TokenValidator tokenValidator;

    @Around("@annotation(innerUser)")
    public Object innerAround(ProceedingJoinPoint point, InnerUser innerUser) throws Throwable {
        HttpServletRequest request = ServletUtils.getRequest();
        if (request == null) {
            throw new OAuthValidateException("错误:未获取到请求信息");
        }
        String source = request.getHeader(RPCConstants.REQ_ORIGIN);
        if (StringUtils.isEmpty(source)) {
            throw new OAuthValidateException("错误:内部接口禁止外部直接访问");
        }
        // 内部请求验证
        if (RPCConstants.INNER.equals(source) && !innerUser.validateUser()) {
            return point.proceed();
        }
        Result result = tokenValidator.validator(request);
        if (!result.isSuccess()) {
            throw new OAuthValidateException(result.getMsg());
        }
        return point.proceed();
    }
}

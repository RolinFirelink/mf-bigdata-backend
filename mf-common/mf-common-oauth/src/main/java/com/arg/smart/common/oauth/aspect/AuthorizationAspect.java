package com.arg.smart.common.oauth.aspect;

import com.arg.smart.common.core.exception.MyRuntimeException;
import com.arg.smart.common.oauth.annotation.RequiresPermissions;
import com.arg.smart.common.oauth.annotation.RequiresRoles;
import com.arg.smart.common.oauth.common.OauthUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @author cgli
 * @description: 权限切面处理
 * @date: 2022/12/5 18:04
 */
@Aspect
@Component
@Slf4j
public class AuthorizationAspect {
    @Before("@annotation(com.arg.smart.common.oauth.annotation.RequiresPermissions)" +
            "||@annotation(com.arg.smart.common.oauth.annotation.RequiresRoles)")
    public void doBefore(JoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        if (method.isAnnotationPresent(RequiresPermissions.class)) {
            if (!OauthUtils.checkPermission(method.getAnnotation(RequiresPermissions.class))) {
                throw new MyRuntimeException("错误:该用户无此操作权限");
            }
        }
        if (method.isAnnotationPresent(RequiresRoles.class)) {
            if (!OauthUtils.checkRoles(method.getAnnotation(RequiresRoles.class))) {
                throw new MyRuntimeException("错误:该角色无此操作访问");
            }
        }
    }

}

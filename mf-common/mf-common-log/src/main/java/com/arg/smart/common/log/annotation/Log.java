package com.arg.smart.common.log.annotation;

import com.arg.smart.common.core.enums.OperateType;
import com.arg.smart.common.core.enums.ReqSource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author cgli
 * @description: 日志记录注解
 * @date: 2022/9/1 16:51
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    /**
     * 标题
     * @return
     */
    String title() default "";

    /**
     * 操作类型
     * @return
     */
    OperateType operateType() default OperateType.OTHER;

    /**
     * 请求来源 默认为管理端
      * @return
     */
    ReqSource reqSource() default ReqSource.MANAGER;
}

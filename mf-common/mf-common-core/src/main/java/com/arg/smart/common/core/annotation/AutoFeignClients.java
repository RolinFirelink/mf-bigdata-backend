package com.arg.smart.common.core.annotation;

import org.springframework.cloud.openfeign.EnableFeignClients;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author cgli
 * @description: Feign注解设置扫描包
 * @date: 2021/12/3 15:40
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@EnableFeignClients
public @interface AutoFeignClients {
    String[] value() default {};

    String[] basePackages() default {"com.arg.smart"};

    Class<?>[] basePackageClasses() default {};

    Class<?>[] defaultConfiguration() default {};

    Class<?>[] clients() default {};
}

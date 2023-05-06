package com.arg.smart.oauth;

import com.arg.smart.common.web.annotation.AutoWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @author cgli
 * @description: 统一认证中心
 * @date: 2021/11/15 15:05
 */
@Slf4j
@AutoWeb
public class MfOauthApplication {
    public static void main(String[] args) {
        SpringApplication.run(MfOauthApplication.class, args);
        log.info("\n\t----------------------------------------------------------\n\t" +
                "\n\t--------------------认证中心启动成功-----------------------\n\t");
    }
}

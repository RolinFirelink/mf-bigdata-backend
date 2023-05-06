package com.arg.smart.test;

import com.arg.smart.common.app.annotation.AutoApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @author cgli
 * @description: 测试中心启动
 * @date: 2021/12/3 17:22
 */
@Slf4j
@AutoApp
public class MfTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(MfTestApplication.class, args);
        log.info("\n\t----------------------------------------------------------\n\t" +
                "\n\t--------------------测试中心启动成功-----------------------\n\t");
    }
}

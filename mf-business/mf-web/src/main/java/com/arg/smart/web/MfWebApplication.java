package com.arg.smart.web;

import com.arg.smart.common.web.annotation.AutoWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author cgli
 * @description: 其他web业务服务参考类
 * @date: 2022/12/16 10:01
 */
@Slf4j
@AutoWeb
@EnableScheduling
public class MfWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(MfWebApplication.class, args);
        log.info("\n\t----------------------------------------------------------\n\t" +
                "\n\t--------------------其他web业务服务启动成功-----------------------\n\t");
    }
}

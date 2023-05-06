package com.arg.smart.monitor;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description: 监控中心启动类
 * @author cgli
 * @date: 2023/1/26 23:11
 */
@SpringBootApplication
@EnableAdminServer
@Slf4j
public class MfMonitorApplication {
    public static void main(String[] args) {
        SpringApplication.run(MfMonitorApplication.class, args);
        log.info("\n\t----------------------------------------------------------\n\t" +
                "\n\t--------------------监控中心启动成功-----------------------\n\t");
    }
}

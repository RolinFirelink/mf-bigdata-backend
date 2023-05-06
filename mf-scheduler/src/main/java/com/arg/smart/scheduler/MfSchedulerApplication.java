package com.arg.smart.scheduler;

import com.arg.smart.common.web.annotation.AutoWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @description: 调度中心启动类
 * @author cgli
 * @date: 2023/2/3 15:15
 */
@AutoWeb
@Slf4j
@EnableScheduling
public class MfSchedulerApplication {
    public static void main(String[] args) {
        SpringApplication.run(MfSchedulerApplication.class, args);
        log.info("\n\t----------------------------------------------------------\n\t" +
                "\n\t--------------------调度中心启动成功-----------------------\n\t");
    }
}

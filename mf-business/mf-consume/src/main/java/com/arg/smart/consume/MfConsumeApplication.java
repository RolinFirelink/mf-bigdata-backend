package com.arg.smart.consume;

import com.arg.smart.common.app.annotation.AutoApp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @description: 消费端
 * @author cgli
 * @date: 2023/3/1 16:25
 */
@Slf4j
@AutoApp
public class MfConsumeApplication {
    public static void main(String[] args) {
        SpringApplication.run(MfConsumeApplication.class, args);
        log.info("\n\t----------------------------------------------------------\n\t" +
                "\n\t--------------------调度中心消费端测试样例启动成功-----------------------\n\t");
    }
}

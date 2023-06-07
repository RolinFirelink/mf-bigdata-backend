package com.arg.smart.report;

import com.arg.smart.report.annotation.AutoWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @author cgli
 * @description: 其他web业务服务参考类
 * @date: 2022/12/16 10:01
 */
@Slf4j
@AutoWeb
public class MfReportApplication {
    public static void main(String[] args) {
        SpringApplication.run(MfReportApplication.class, args);
        log.info("\n\t----------------------------------------------------------\n\t" +
                "\n\t--------------------report服务启动成功-----------------------\n\t");
    }
}

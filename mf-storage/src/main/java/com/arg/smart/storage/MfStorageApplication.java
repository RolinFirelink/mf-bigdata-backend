package com.arg.smart.storage;

import com.arg.smart.common.web.annotation.AutoWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @description: 文件中心启动类
 * @author cgli
 * @date: 2023/1/5 16:34
 */
@Slf4j
@AutoWeb
public class MfStorageApplication {
    public static void main(String[] args) {
        SpringApplication.run(MfStorageApplication.class, args);
        log.info("\n\t----------------------------------------------------------\n\t" +
                "\n\t--------------------文件中心启动成功-----------------------\n\t");
    }
}

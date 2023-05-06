package com.arg.smart.code;

import com.arg.smart.common.web.annotation.AutoWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @author cgli
 * @description: 代码生成中心启动类
 * @date: 2022/8/18 16:42
 */
@Slf4j
@AutoWeb
public class MfCodeApplication {
    public static void main(String[] args) {
        SpringApplication.run(MfCodeApplication.class, args);
        log.info("\n\t----------------------------------------------------------\n\t" +
                "\n\t--------------------代码生成中心启动成功-----------------------\n\t");
    }
}

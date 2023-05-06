package com.arg.smart.openai;

import com.arg.smart.common.web.annotation.AutoWeb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

/**
 * @description: 聊天机器人
 * @author cgli
 * @date: 2023/2/8
 */
@AutoWeb
@Slf4j
public class MfOpenAiApplication {
    public static void main(String[] args) {
        SpringApplication.run(MfOpenAiApplication.class, args);
        log.info("\n\t----------------------------------------------------------\n\t" +
                "\n\t--------------------聊天机器人启动成功-----------------------\n\t");
    }
}

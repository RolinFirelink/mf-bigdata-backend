package com.arg.smart.scheduler.job;

import com.arg.smart.sys.api.entity.SysLog;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

/**
 * @description: 任务逻辑(样例)
 * @author cgli
 * @date: 2023/2/13 20:07
 */
@Component("mfJob")
@Slf4j
public class MfJob {

    public void test() {
        log.info("任务执行");
    }

    public void test(String param) {
        log.info(MessageFormat.format("任务执行!参数:{0}", param));
    }

    public void test(String param1, Integer param2) {
        log.info(MessageFormat.format("任务执行!参数1:{0},参数:{1}", param1, param2));
    }

    public void test(String param1, String param2) {
        log.info(MessageFormat.format("任务执行!参数1:{0},参数:{1}", param1, param2));
    }

    public void test(String params1, SysLog params2) {
        log.info(MessageFormat.format("任务执行!参数1:{0},参数:{1}", params1, params2));
    }
}

package com.arg.smart.sys.api.fallback;


import com.arg.smart.common.core.web.Result;
import com.arg.smart.sys.api.remote.RemoteLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author cgli
 * @description: 日志远程调用异常回调
 * @date: 2022/9/4 11:47
 */
@Slf4j
@Component
public class RemoteLogFallback implements FallbackFactory<RemoteLogService> {
    @Override
    public RemoteLogService create(Throwable cause) {
        log.error("错误:日志服务调用异常", cause);
        return (origin, sysLog) -> Result.fail("错误:新增日志失败");
    }
}
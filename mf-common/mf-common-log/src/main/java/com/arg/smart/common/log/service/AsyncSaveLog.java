package com.arg.smart.common.log.service;

import com.arg.smart.common.core.constants.RPCConstants;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.sys.api.entity.SysLog;
import com.arg.smart.sys.api.remote.RemoteLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author cgli
 * @description: 异步保存日志
 * @date: 2022/9/7 15:24
 */
@Service
@EnableAsync
@Slf4j
public class AsyncSaveLog {
    @Resource
    RemoteLogService remoteLogService;

    @Async
    public void saveLog(SysLog sysLog) {
        Result result = remoteLogService.addLog(RPCConstants.INNER, sysLog);
        if (result.isSuccess()) {
            return;
        }
        log.error("错误:保存日志出错", result.getMsg());
    }
}

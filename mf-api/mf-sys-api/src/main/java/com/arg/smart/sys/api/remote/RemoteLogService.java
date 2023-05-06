package com.arg.smart.sys.api.remote;

import com.arg.smart.common.core.constants.RPCConstants;
import com.arg.smart.common.core.constants.ServiceConstants;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.sys.api.entity.SysLog;
import com.arg.smart.sys.api.fallback.RemoteLogFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @author cgli
 * @description: 日志对外服务
 * @date: 2022/9/4
 */
@FeignClient(contextId = "remoteLogService", value = ServiceConstants.SYS_SERVICE, fallbackFactory = RemoteLogFallback.class)
public interface RemoteLogService {
    @PostMapping("/sysLog")
    Result<SysLog> addLog(@RequestHeader(RPCConstants.REQ_ORIGIN) String origin, @RequestBody SysLog sysLog);
}

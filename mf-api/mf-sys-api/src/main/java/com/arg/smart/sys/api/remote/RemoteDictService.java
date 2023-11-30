package com.arg.smart.sys.api.remote;

import com.arg.smart.common.core.constants.RPCConstants;
import com.arg.smart.common.core.constants.ServiceConstants;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.sys.api.entity.SysLog;
import com.arg.smart.sys.api.fallback.RemoteDbConnectFallback;
import com.arg.smart.sys.api.fallback.RemoteDictFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

/**
 * @description: 字段对外服务
 * @author zsj
 * @date: 2023/11/15 17:25
 */

@FeignClient(contextId = "remoteDictService", value = ServiceConstants.SYS_SERVICE, fallbackFactory = RemoteDictFallback.class)
public interface RemoteDictService {

    @GetMapping("/dictItem/getProductTypeCount")
    Integer getProductCount(@RequestHeader(RPCConstants.REQ_ORIGIN) String origin);
}

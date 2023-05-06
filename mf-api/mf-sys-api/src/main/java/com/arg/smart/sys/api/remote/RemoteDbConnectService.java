package com.arg.smart.sys.api.remote;

import com.arg.smart.common.core.constants.RPCConstants;
import com.arg.smart.common.core.constants.ServiceConstants;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.sys.api.entity.DbConnect;
import com.arg.smart.sys.api.entity.FieldInfo;
import com.arg.smart.sys.api.entity.TableInfo;
import com.arg.smart.sys.api.fallback.RemoteDbConnectFallback;
import com.arg.smart.sys.api.req.ReqDbConnect;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 数据库连接服务
 * @author cgli
 * @date: 2023/3/23 21:38
 */
@FeignClient(contextId = "remoteDbConnectService", value = ServiceConstants.SYS_SERVICE, fallbackFactory = RemoteDbConnectFallback.class)
public interface RemoteDbConnectService {
    @GetMapping("/dbConnect")
    Result<PageResult<DbConnect>> queryPageList(@SpringQueryMap ReqDbConnect reqDbConnect, @SpringQueryMap ReqPage reqPage);

    @GetMapping("/dbConnect/{id}")
    Result<DbConnect> queryById(@RequestHeader(RPCConstants.REQ_ORIGIN) String origin, @PathVariable("id") String id);

    @GetMapping("/dbConnect/tables")
    Result<PageResult<TableInfo>> getTableList(@RequestParam(name = "connectId") String connectId, @RequestParam(name = "tableName", required = false) String tableName, @SpringQueryMap ReqPage reqPage);

    @GetMapping("/dbConnect/fields")
    Result<PageResult<FieldInfo>> getFieldList(@RequestParam(name = "connectId") String connectId, @RequestParam(name = "tableName", required = false) String tableName, @SpringQueryMap ReqPage reqPage);
}

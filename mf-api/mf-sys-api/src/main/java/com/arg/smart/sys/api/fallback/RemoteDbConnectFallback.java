package com.arg.smart.sys.api.fallback;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.sys.api.entity.DbConnect;
import com.arg.smart.sys.api.entity.FieldInfo;
import com.arg.smart.sys.api.entity.TableInfo;
import com.arg.smart.sys.api.remote.RemoteDbConnectService;
import com.arg.smart.sys.api.req.ReqDbConnect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @description: 远程数据连接失败处理
 * @author cgli
 * @date: 2023/3/23 21:39
 */
@Slf4j
@Component
public class RemoteDbConnectFallback implements FallbackFactory<RemoteDbConnectService> {
    @Override
    public RemoteDbConnectService create(Throwable cause) {
        log.error("错误:数据库连接调用异常", cause);
        return new RemoteDbConnectService() {
            @Override
            public Result<PageResult<DbConnect>> queryPageList(ReqDbConnect reqDbConnect, ReqPage reqPage) {
                return Result.fail("错误:查询数据库连接列表出错");
            }

            @Override
            public Result<DbConnect> queryById(String origin, String id) {
                return Result.fail("错误:查询数据库连接信息出错");
            }

            @Override
            public Result<PageResult<TableInfo>> getTableList(String connectId, String tableName, ReqPage reqPage) {
                return Result.fail("错误:查询数据库表列表出错");
            }

            @Override
            public Result<PageResult<FieldInfo>> getFieldList(String connectId, String tableName, ReqPage reqPage) {
                return Result.fail("错误:查询表字段列表出错");
            }
        };
    }
}

package com.arg.smart.sys.api.fallback;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.sys.api.entity.DbConnect;
import com.arg.smart.sys.api.entity.FieldInfo;
import com.arg.smart.sys.api.entity.TableInfo;
import com.arg.smart.sys.api.remote.RemoteDbConnectService;
import com.arg.smart.sys.api.remote.RemoteDictService;
import com.arg.smart.sys.api.remote.RemoteLogService;
import com.arg.smart.sys.api.req.ReqDbConnect;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author zsj
 * @description: 字典远程调用异常回调
 * @date: 2023/11/15 17.27
 */
@Slf4j
@Component
public class RemoteDictFallback implements FallbackFactory<RemoteDictService> {

    @Override
    public RemoteDictService create(Throwable cause) {
        log.error("错误:字典服务调用异常", cause);
        return origin -> 0;
    }
}

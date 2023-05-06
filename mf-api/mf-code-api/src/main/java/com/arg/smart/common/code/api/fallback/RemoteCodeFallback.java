package com.arg.smart.common.code.api.fallback;

import com.arg.smart.common.code.api.remote.RemoteCodeService;
import com.arg.smart.common.code.api.req.ReqCode;
import com.arg.smart.common.code.api.vo.CodeVo;
import com.arg.smart.common.core.web.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author cgli
 * @description: Feign用户接口降级
 * @date: 2021/12/4 0:27
 */
@Component
@Slf4j
public class RemoteCodeFallback implements FallbackFactory<RemoteCodeService> {

    @Override
    public RemoteCodeService create(Throwable cause) {
        log.error("代码生成服务调用失败:" + cause.getMessage());
        return new RemoteCodeService() {
            @Override
            public Result<List<CodeVo>> getCode(ReqCode reqCode) {
                return Result.fail("错误:查询代码出错");
            }

            @Override
            public Result<String> saveCode(ReqCode reqCode) {
                return Result.fail("错误:生成代码出错");
            }

            @Override
            public Result<byte[]> downloadCode(ReqCode reqCode) {
                return Result.fail("错误:下载代码出错");
            }
        };
    }
}

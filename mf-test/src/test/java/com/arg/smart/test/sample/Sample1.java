package com.arg.smart.test.sample;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.redis.common.IDBuild;
import com.arg.smart.sys.api.entity.DbConnect;
import com.arg.smart.sys.api.remote.RemoteDbConnectService;
import com.arg.smart.sys.api.req.ReqDbConnect;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;

/**
 * @description: 测试类
 * @author cgli
 * @date: 2023/3/5 14:59
 */
@Slf4j
@SpringBootTest
@ComponentScan(basePackages = "com.arg.smart.test")
public class Sample1 {
    @Resource
    RemoteDbConnectService remoteDbConnectService;

    @Test
    public void testIDBuild() {
        for (int i = 0; i < 2000; i++) {
            CompletableFuture.runAsync(() -> System.out.println(IDBuild.getID("File")));
        }
        try {
            Thread.sleep(60000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testController() {
        Result<PageResult<DbConnect>> result = remoteDbConnectService.queryPageList(new ReqDbConnect().setDbName("aaa"), new ReqPage());
        System.out.println(result);
    }
}

package com.arg.smart.web.cms.service;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.common.utils.MD5Utils;
import com.arg.smart.web.MfWebApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author rainkyzhong
 * @date 2023/8/11 22:42
 */
@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class RemoteServiceTest {
    @Resource
    private RemoteArticleService remoteArticleService;

    @Test
    public void test(){
        remoteArticleService.fetch(0L, 100);
    }

    @Test
    public void md5Test(){
        String test ="id=0&len=100&content=1";
        String md5ed = MD5Utils.md5Hex(test, StandardCharsets.UTF_8.name());
        System.out.println("md5="+md5ed);
    }
}

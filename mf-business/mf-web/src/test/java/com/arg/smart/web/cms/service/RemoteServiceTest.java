package com.arg.smart.web.cms.service;

import com.arg.smart.web.MfWebApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

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
        remoteArticleService.fetch(1L,10);
    }
}

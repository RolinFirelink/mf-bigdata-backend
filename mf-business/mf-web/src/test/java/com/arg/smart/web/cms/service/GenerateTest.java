package com.arg.smart.web.cms.service;

import com.arg.smart.web.MfWebApplication;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class GenerateTest {

    @Resource
    private ArticleGenerationService articleGenerationService;

    @Test
    public void test(){
        articleGenerationService.generatePriceDaily(5);
    }
}

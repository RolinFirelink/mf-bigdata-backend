package com.arg.smart.web.report.service.impl;

import com.arg.smart.web.MfWebApplication;
import com.arg.smart.web.report.service.GoodAnalyseService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Map;

import static org.junit.Assert.*;

@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class GoodAnalyseServiceImplTest {

    @Resource
    private GoodAnalyseService goodAnalyseService;

    @Test
    public void testGetGoodAnalyseReport() {
        Map<String, Object> map = goodAnalyseService.getGoodAnalyseReport("乌鸡", "白羽乌鸡", 2023, 8);
        Assert.assertNotNull(map);
        log.info("Good Analyse Report: \n {} \n", map.get("report"));
    }

}
package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.web.MfWebApplication;
import com.arg.smart.web.statistics.entity.vo.StatisticsData;
import com.arg.smart.web.statistics.service.StatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class StatisticsTest {

    @Resource
    private StatisticsService statisticsService;
}



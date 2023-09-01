package com.arg.smart.web.report.service.impl;

import com.arg.smart.web.MfWebApplication;
import com.arg.smart.web.order.model.ModuleFlag;
import com.arg.smart.web.report.service.PricePredictService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class PricePredictServiceImplTest {

    @Resource
    private PricePredictService service;

    @Test
    public void testGetWeekAveragePrice() {

    }

    @Test
    public void testGetPriceDescription() {
        String description = service.getPriceDescription(ModuleFlag.CHICKEN);
        log.info("Description: {}", description);
    }

}
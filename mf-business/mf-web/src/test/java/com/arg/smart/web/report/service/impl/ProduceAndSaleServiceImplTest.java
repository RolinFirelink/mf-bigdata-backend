package com.arg.smart.web.report.service.impl;

import com.arg.smart.web.MfWebApplication;
import com.arg.smart.web.order.model.ModuleFlag;
import com.arg.smart.web.report.service.ProduceAndSaleService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * @Author GlowingTree
 * @Description TODO
 * @Date 8/24/2023 2:40 PM
 * @Version 1.0
 */
@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class ProduceAndSaleServiceImplTest {

	@Resource
	private ProduceAndSaleService produceAndSaleService;

	@Test
	public void testGetProductAndSaleReport() {
		String report = produceAndSaleService.getProductAndSaleReport(ModuleFlag.ORANGE, 2023, 8);
		Assert.assertNotNull(report);
		log.info("Product And Sale Report: \n {} \n", report);
	}

}
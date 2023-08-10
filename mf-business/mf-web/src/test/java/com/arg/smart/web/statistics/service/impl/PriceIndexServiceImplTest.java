package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.web.MfWebApplication;
import com.arg.smart.web.statistics.service.PriceIndexService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @Author GlowingTree
 * @Description TODO
 * @Date 8/9/2023 2:23 PM
 * @Version 1.0
 */
@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class PriceIndexServiceImplTest {

	@Resource
	private PriceIndexService priceIndexService;

	@Test
	public void testUpdatePriceIndex() {
		priceIndexService.updatePriceIndex();
	}

}
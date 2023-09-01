package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.web.MfWebApplication;
import com.arg.smart.web.statistics.entity.PriceIndex;
import com.arg.smart.web.statistics.req.ReqBuyersIndex;
import com.arg.smart.web.statistics.req.ReqPriceIndex;
import com.arg.smart.web.statistics.service.PriceIndexService;
import com.arg.smart.web.statistics.vo.BuyersIndexVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author GlowingTree
 * @Description 采购商价格指数测试类
 * @Date 8/9/2023 2:23 PM
 * @Version 1.0
 */
@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class PriceIndexServiceImplTest {

	@Resource
	private PriceIndexService priceIndexService;

	private ReqPriceIndex reqPriceIndex;
	private ReqBuyersIndex reqBuyersIndex;

	@Before
	public void setUp() {
		reqPriceIndex = new ReqPriceIndex();
		reqPriceIndex.setFlag(1);
		reqPriceIndex.setYear(2023);
		reqPriceIndex.setMonth(7);

		reqBuyersIndex = new ReqBuyersIndex();
		reqBuyersIndex.setFlag(1);
		reqBuyersIndex.setYear(2023);
		reqBuyersIndex.setMonth(7);
	}

	@Test
	public void testUpdatePriceIndex() {
		priceIndexService.updatePriceIndex();
	}

	@Test
	public void testUpdatePriceIndexByMonth() {
		priceIndexService.updatePriceIndex(2023, 7);
	}

	@Test
	public void testGetPriceIndex() {
		List<PriceIndex> priceIndexes = priceIndexService.list(reqPriceIndex);
		Assert.assertNotNull(priceIndexes);
		log.info("price indexes: {}", priceIndexes);
	}

	@Test
	public void testGetBuyersIndex() {
		BuyersIndexVO index = priceIndexService.list(reqBuyersIndex);
		Assert.assertNotNull(index);
		log.info("buyers index: {}", index);
	}

}
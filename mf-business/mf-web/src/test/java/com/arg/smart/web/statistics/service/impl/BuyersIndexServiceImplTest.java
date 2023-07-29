package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.web.MfWebApplication;
import com.arg.smart.web.statistics.req.ReqBuyersIndex;
import com.arg.smart.web.statistics.service.BuyersIndexService;
import com.arg.smart.web.statistics.vo.BuyersIndexVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author GlowingTree
 * @Description TODO
 * @Date 7/30/2023 1:04 AM
 * @Version 1.0
 */
@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class BuyersIndexServiceImplTest {

	@Resource
	private BuyersIndexService buyersIndexService;

	@Test
	public void testGetBuyersIndex() {
		ReqBuyersIndex req = new ReqBuyersIndex();
		req.setYear(2023);
		req.setMonth(7);
		List<BuyersIndexVO> indexes = buyersIndexService.getBuyersIndex(req);
		assertNotNull(indexes);
		log.info("buyers index list: {}", indexes);
	}

}
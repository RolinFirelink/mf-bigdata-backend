package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.web.MfWebApplication;
import com.arg.smart.web.order.model.ModuleFlag;
import com.arg.smart.web.statistics.req.ReqMarketStatistics;
import com.arg.smart.web.statistics.service.MarketStatisticsService;
import com.arg.smart.web.statistics.vo.MarketStatisticsVO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @Author GlowingTree
 * @Description TODO
 * @Date 7/22/2023 7:42 PM
 * @Version 1.0
 */
@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class MarketStatisticsServiceImplTest {

	@Resource
	private MarketStatisticsService marketStatisticsService;

	private ReqMarketStatistics reqMarketStatistics;

	@Before
	public void setUp() {
		try {
			reqMarketStatistics = new ReqMarketStatistics();

			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			reqMarketStatistics.setStartTime(sdf.parse("2023-06-06"));
			reqMarketStatistics.setEndTime(sdf.parse("2023-07-22"));
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetMarketStatisticsVO() {
		reqMarketStatistics.setFlag(ModuleFlag.ORANGE);
		List<MarketStatisticsVO> list = marketStatisticsService.list(reqMarketStatistics);
		assertNotNull(list);
		log.info("Market Statistics list: {}", list);
	}
}
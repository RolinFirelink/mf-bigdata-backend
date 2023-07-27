package com.arg.smart.web.statistics.mapper;

import com.arg.smart.web.statistics.entity.MarketStatistics;
import com.arg.smart.web.statistics.req.ReqMarketStatistics;
import com.arg.smart.web.statistics.vo.MarketStatisticsVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * @description: 市场行情统计表
 * @author cgli
 * @date: 2023-07-17
 * @version: V1.0.0
 */
public interface MarketStatisticsMapper extends BaseMapper<MarketStatistics> {
	List<MarketStatisticsVO> list(ReqMarketStatistics reqMarketStatistics);
}

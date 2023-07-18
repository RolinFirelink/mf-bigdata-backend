package com.arg.smart.web.statistics.service;

import com.arg.smart.web.statistics.entity.MarketStatistics;
import com.arg.smart.web.statistics.req.ReqMarketStatistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 市场行情统计表
 * @author cgli
 * @date: 2023-07-17
 * @version: V1.0.0
 */
public interface MarketStatisticsService extends IService<MarketStatistics> {

    List<MarketStatistics> list(ReqMarketStatistics reqMarketStatistics);
}

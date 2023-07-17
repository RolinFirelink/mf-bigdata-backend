package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.web.statistics.entity.MarketStatistics;
import com.arg.smart.web.statistics.mapper.MarketStatisticsMapper;
import com.arg.smart.web.statistics.req.ReqMarketStatistics;
import com.arg.smart.web.statistics.service.MarketStatisticsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 市场行情统计表
 * @author cgli
 * @date: 2023-07-17
 * @version: V1.0.0
 */
@Service
public class MarketStatisticsServiceImpl extends ServiceImpl<MarketStatisticsMapper, MarketStatistics> implements MarketStatisticsService {

    @Override
    public List<MarketStatistics> list(ReqMarketStatistics reqMarketStatistics) {
        Integer flag = reqMarketStatistics.getFlag();
        LambdaQueryWrapper<MarketStatistics> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MarketStatistics::getFlag,flag);
        return this.list(queryWrapper);
    }
}

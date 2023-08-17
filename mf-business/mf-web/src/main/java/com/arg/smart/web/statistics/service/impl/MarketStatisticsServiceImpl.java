package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.web.statistics.entity.MarketStatistics;
import com.arg.smart.web.statistics.mapper.MarketStatisticsMapper;
import com.arg.smart.web.statistics.req.ReqMarketStatistics;
import com.arg.smart.web.statistics.service.MarketStatisticsService;
import com.arg.smart.web.statistics.vo.MarketStatisticsVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cgli
 * @description: 市场行情统计表
 * @date: 2023-07-17
 * @version: V1.0.0
 */
@Service
public class MarketStatisticsServiceImpl extends ServiceImpl<MarketStatisticsMapper, MarketStatistics> implements MarketStatisticsService {

    @Resource
    private MarketStatisticsMapper mapper;

    @Override
    public List<MarketStatisticsVO> list(ReqMarketStatistics reqMarketStatistics) {
        return mapper.list(reqMarketStatistics);
    }

    @Override
    public List<MarketStatistics> pageList(ReqMarketStatistics reqMarketStatistics) {
        LambdaQueryWrapper<MarketStatistics> queryWrapper = new LambdaQueryWrapper<>();
        String city = reqMarketStatistics.getCity();
        Integer flag = reqMarketStatistics.getFlag();
        queryWrapper.like(city != null, MarketStatistics::getCity, city)
                .eq(flag != null, MarketStatistics::getFlag, flag);
        return this.list(queryWrapper);
    }

}

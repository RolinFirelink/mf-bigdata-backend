package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.web.statistics.entity.MarketStatistics;
import com.arg.smart.web.statistics.mapper.MarketStatisticsMapper;
import com.arg.smart.web.statistics.req.ReqMarketStatistics;
import com.arg.smart.web.statistics.service.MarketStatisticsService;
import com.arg.smart.web.statistics.vo.MarketStatisticsVO;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 市场行情统计表
 * @author cgli
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

}

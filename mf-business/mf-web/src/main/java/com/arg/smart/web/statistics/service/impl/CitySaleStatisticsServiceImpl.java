package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.web.statistics.entity.CitySaleStatistics;
import com.arg.smart.web.statistics.mapper.CitySaleStatisticsMapper;
import com.arg.smart.web.statistics.req.ReqCitySaleStatistics;
import com.arg.smart.web.statistics.service.CitySaleStatisticsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 城市销售量表
 * @date: 2023-07-17
 * @version: V1.0.0
 */
@Service
public class CitySaleStatisticsServiceImpl extends ServiceImpl<CitySaleStatisticsMapper, CitySaleStatistics> implements CitySaleStatisticsService {

    @Override
    public List<CitySaleStatistics> list(ReqCitySaleStatistics reqCitySaleStatistics) {
        Integer flag = reqCitySaleStatistics.getFlag();
        Date startTime = reqCitySaleStatistics.getStartTime();
        Date endTime = reqCitySaleStatistics.getEndTime();
        QueryWrapper<CitySaleStatistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id, city, SUM(sales) As sales, unit, flag, product, statistics_time")
                .lambda()
                .eq(CitySaleStatistics::getFlag, flag)
                .between(startTime != null && endTime != null, CitySaleStatistics::getStatisticsTime, startTime, endTime)
                .orderByDesc(CitySaleStatistics::getSales)
                .groupBy(CitySaleStatistics::getCity);
        Integer count = reqCitySaleStatistics.getCount();
        if (count != null) {
            queryWrapper.last("limit " + count);
        }
        return this.list(queryWrapper);
    }
}

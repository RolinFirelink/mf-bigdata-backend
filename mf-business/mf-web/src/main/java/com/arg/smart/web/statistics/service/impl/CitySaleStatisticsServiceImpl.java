package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.web.statistics.entity.CitySaleStatistics;
import com.arg.smart.web.statistics.mapper.CitySaleStatisticsMapper;
import com.arg.smart.web.statistics.req.ReqCitySaleStatistics;
import com.arg.smart.web.statistics.service.CitySaleStatisticsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;

/**
 * @description: 城市销售量表
 * @author cgli
 * @date: 2023-07-17
 * @version: V1.0.0
 */
@Service
public class CitySaleStatisticsServiceImpl extends ServiceImpl<CitySaleStatisticsMapper, CitySaleStatistics> implements CitySaleStatisticsService {

    @Override
    public List<CitySaleStatistics> list(ReqCitySaleStatistics reqCitySaleStatistics) {
        Integer flag = reqCitySaleStatistics.getFlag();
        LambdaQueryWrapper<CitySaleStatistics> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CitySaleStatistics::getFlag,flag);
        queryWrapper.orderByDesc(CitySaleStatistics::getSales);
        Integer count = reqCitySaleStatistics.getCount();
        if(count == null){
            // 默认返回10个
            count = 10;
        }
        queryWrapper.last("limit "+count);
        return this.list(queryWrapper);
    }
}

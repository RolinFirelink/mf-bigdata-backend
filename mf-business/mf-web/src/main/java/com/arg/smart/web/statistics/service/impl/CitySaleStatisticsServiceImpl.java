package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.web.statistics.entity.CitySaleStatistics;
import com.arg.smart.web.statistics.mapper.CitySaleStatisticsMapper;
import com.arg.smart.web.statistics.req.ReqCitySaleStatistics;
import com.arg.smart.web.statistics.service.CitySaleStatisticsService;
import com.arg.smart.web.statistics.vo.CitySaleStatisticsVO;
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
    public List<CitySaleStatisticsVO> list(ReqCitySaleStatistics reqCitySaleStatistics) {
        Integer flag = reqCitySaleStatistics.getFlag();
        Date startTime = reqCitySaleStatistics.getStartTime();
        Date endTime = reqCitySaleStatistics.getEndTime();
        QueryWrapper<CitySaleStatistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("city, SUM(sales) As sales, unit")
                .lambda()
                .eq(CitySaleStatistics::getFlag, flag)
                .ge(startTime != null, CitySaleStatistics::getStatisticsTime, startTime)
                .le(endTime != null, CitySaleStatistics::getStatisticsTime, endTime)
                .orderByDesc(CitySaleStatistics::getSales)
                .groupBy(CitySaleStatistics::getCity).groupBy(CitySaleStatistics::getUnit);
        Integer count = reqCitySaleStatistics.getCount();
        if (count != null) {
            queryWrapper.last("limit " + count);
        }

        return this.list(queryWrapper).stream().map(item -> {
            CitySaleStatisticsVO citySaleStatisticsVO = new CitySaleStatisticsVO();
            citySaleStatisticsVO.setCity(item.getCity());
            citySaleStatisticsVO.setUnit(item.getUnit());
            citySaleStatisticsVO.setSales(item.getSales());
            return citySaleStatisticsVO;
        }).collect(Collectors.toList());
    }

    @Override
    public List<CitySaleStatistics> getMainCityData(ReqCitySaleStatistics reqCitySaleStatistics) {
        QueryWrapper<CitySaleStatistics> queryWrapper = new QueryWrapper<>();
        Integer flag = reqCitySaleStatistics.getFlag();
        Date startTime = reqCitySaleStatistics.getStartTime();
        Date endTime = reqCitySaleStatistics.getEndTime();
        String cities = reqCitySaleStatistics.getCities();
        if(cities != null){
            List<String> cityList = Arrays.stream(cities.split(";")).collect(Collectors.toList());
            queryWrapper.in("city",cityList);
        }
        queryWrapper.eq("flag", flag)
                .ge(startTime != null, "statistics_time", startTime)
                .le(endTime != null, "statistics_time", endTime);
        return this.list(queryWrapper);
    }
}

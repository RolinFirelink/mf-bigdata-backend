package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.statistics.entity.ProductionStatistics;
import com.arg.smart.web.statistics.mapper.ProductionStatisticsMapper;
import com.arg.smart.web.product.req.ReqProductionStatistics;
import com.arg.smart.web.statistics.service.ProductionStatisticsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author cgli
 * @description: 生产统计
 * @date: 2023-07-15
 * @version: V1.0.0
 */
@Service
public class ProductionStatisticsServiceImpl extends ServiceImpl<ProductionStatisticsMapper, ProductionStatistics> implements ProductionStatisticsService {

    @Override
    public List<ProductionStatistics> list(ReqProductionStatistics reqProductionStatistics) {
        QueryWrapper<ProductionStatistics> queryWrapper = new QueryWrapper<>();
        Integer searchType = reqProductionStatistics.getSearchType();
        Integer count = reqProductionStatistics.getCount();
        Date startTime = reqProductionStatistics.getStartTime();
        Date endTime = reqProductionStatistics.getEndTime();
        // flag不能为空
        Integer flag = reqProductionStatistics.getFlag();
        queryWrapper.eq("flag", flag).
                orderByDesc("year");
        queryWrapper.ge(startTime != null, "statistical_time", startTime);
        queryWrapper.le(endTime != null, "statistical_time", endTime);
        if (count == null) {
            count = 5;
        }
        if (searchType == null || searchType != 1) {
            //查询月份
            queryWrapper.orderByDesc("month");
            queryWrapper.select("year", "month", "produce_scale", "yield", "yield_unit");
        } else {
            //查询年份
            queryWrapper.groupBy("year").groupBy("yield_unit");
            queryWrapper.select("year", "sum(produce_scale) as produce_scale", "sum(yield) as yield", "yield_unit");
        }
        queryWrapper.last("limit " + count);
        return this.list(queryWrapper);
    }

    @Override
    public PageResult<ProductionStatistics> listPage(ReqProductionStatistics reqProductionStatistics) {
        LambdaQueryWrapper<ProductionStatistics> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(reqProductionStatistics.getFlag() != null, ProductionStatistics::getFlag, reqProductionStatistics.getFlag())
                .between(reqProductionStatistics.getStartTime() != null && reqProductionStatistics.getEndTime() != null, ProductionStatistics::getStatisticalTime, reqProductionStatistics.getStartTime(), reqProductionStatistics.getEndTime());
        return new PageResult<>(this.list(queryWrapper));
    }
}

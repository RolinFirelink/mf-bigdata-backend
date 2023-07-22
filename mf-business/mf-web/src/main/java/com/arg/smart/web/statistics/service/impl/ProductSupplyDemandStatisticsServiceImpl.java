package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.web.statistics.entity.ProductSupplyDemandStatistics;
import com.arg.smart.web.statistics.mapper.ProductSupplyDemandStatisticsMapper;
import com.arg.smart.web.statistics.req.ReqProductSupplyDemandStatistics;
import com.arg.smart.web.statistics.service.ProductSupplyDemandStatisticsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Queue;

/**
 * @description: 产品供需统计表
 * @author cgli
 * @date: 2023-07-17
 * @version: V1.0.0
 */
@Service
public class ProductSupplyDemandStatisticsServiceImpl extends ServiceImpl<ProductSupplyDemandStatisticsMapper, ProductSupplyDemandStatistics> implements ProductSupplyDemandStatisticsService {

    @Override
    public List<ProductSupplyDemandStatistics> list(ReqProductSupplyDemandStatistics reqProductSupplyDemandStatistics) {
        Integer flag = reqProductSupplyDemandStatistics.getFlag();
        QueryWrapper<ProductSupplyDemandStatistics> queryWrapper = new QueryWrapper<>();
        Date startDate = reqProductSupplyDemandStatistics.getStartDate();
        Date endDate = reqProductSupplyDemandStatistics.getEndDate();
        queryWrapper.eq(flag!= null,"flag",flag)
                .ge(startDate != null,"statistics_time",startDate)
                .le(endDate != null ,"statistics_time",endDate)
                .orderByDesc("supply")
                .orderByDesc("demand")
                .select("product","unit","sum(supply) as supply","sum(demand) as demand")
                .groupBy("product","unit");
        return this.list(queryWrapper);
    }
}

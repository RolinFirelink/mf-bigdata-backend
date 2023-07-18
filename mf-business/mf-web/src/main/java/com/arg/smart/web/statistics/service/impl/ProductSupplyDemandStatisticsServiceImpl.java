package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.web.statistics.entity.ProductSupplyDemandStatistics;
import com.arg.smart.web.statistics.mapper.ProductSupplyDemandStatisticsMapper;
import com.arg.smart.web.statistics.req.ReqProductSupplyDemandStatistics;
import com.arg.smart.web.statistics.service.ProductSupplyDemandStatisticsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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
        LambdaQueryWrapper<ProductSupplyDemandStatistics> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(flag!= null,ProductSupplyDemandStatistics::getFlag,flag).
                orderByDesc(ProductSupplyDemandStatistics::getSupply).orderByDesc(ProductSupplyDemandStatistics::getDemand);
        return this.list(queryWrapper);
    }
}

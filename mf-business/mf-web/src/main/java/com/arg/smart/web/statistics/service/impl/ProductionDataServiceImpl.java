package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.web.statistics.entity.ProductionData;
import com.arg.smart.web.statistics.mapper.ProductionDataMapper;
import com.arg.smart.web.statistics.req.ReqProductionData;
import com.arg.smart.web.statistics.service.ProductionDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @description: 地区生产宏观数据
 * @author cgli
 * @date: 2023-10-19
 * @version: V1.0.0
 */
@Service
public class ProductionDataServiceImpl extends ServiceImpl<ProductionDataMapper, ProductionData> implements ProductionDataService {

    @Override
    public List<ProductionData> list(ReqProductionData reqProductionData) {
        String city = reqProductionData.getCity();
        Integer flag = reqProductionData.getFlag();
        LambdaQueryWrapper<ProductionData> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(flag != null,ProductionData::getFlag,flag);
        queryWrapper.like(city != null,ProductionData::getCity,city);
        return this.list(queryWrapper);
    }
}

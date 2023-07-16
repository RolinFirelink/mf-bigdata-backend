package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.statistics.entity.ProductCount;
import com.arg.smart.web.statistics.entity.ProductionStatistics;
import com.arg.smart.web.statistics.mapper.ProductCountMapper;
import com.arg.smart.web.statistics.req.ReqProductCount;
import com.arg.smart.web.statistics.service.ProductCountService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 城市产品生产统计表
 * @author cgli
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Service
public class ProductCountServiceImpl extends ServiceImpl<ProductCountMapper, ProductCount> implements ProductCountService {


    @Override
    public List<ProductCount> list(ReqProductCount reqProductCount) {
        LambdaQueryWrapper<ProductCount> queryWrapper = new LambdaQueryWrapper<>();
        Integer flag = reqProductCount.getFlag();
        queryWrapper.eq(ProductCount::getFlag,flag);
        return this.list(queryWrapper);
    }

    @Override
    public PageResult<ProductCount> listPage(ReqProductCount reqProductCount) {
        LambdaQueryWrapper<ProductCount> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(reqProductCount.getFlag() != null, ProductCount::getFlag, reqProductCount.getFlag())
                .like(reqProductCount.getCity()!=null,ProductCount::getCity, reqProductCount.getCity())
                .between(reqProductCount.getStartTime() != null && reqProductCount.getEndTime() != null,ProductCount::getTime,reqProductCount.getStartTime(),reqProductCount.getEndTime());
        return new PageResult<>(this.list(queryWrapper));
    }
}

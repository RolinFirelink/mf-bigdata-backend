package com.arg.smart.web.statistics.service.impl;


import com.arg.smart.web.data.entity.CompanySales;
import com.arg.smart.web.data.mapper.CompanySalesMapper;
import com.arg.smart.web.data.service.CompanySalesService;
import com.arg.smart.web.statistics.entity.MarketConcentration;
import com.arg.smart.web.statistics.entity.PurchasingHeat;
import com.arg.smart.web.statistics.mapper.MarketConcentrationMapper;
import com.arg.smart.web.statistics.service.MarketConcentrationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 市场集中度
 * @author cgli
 * @date: 2023-08-24
 * @version: V1.0.0
 */
@Service
public class MarketConcentrationServiceImpl extends ServiceImpl<MarketConcentrationMapper, MarketConcentration> implements MarketConcentrationService {


    @Resource
    private CompanySalesMapper companySalesMapper;
    @Override
    @Transactional
    public void updateData() {

        }



    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updateMarketConcentration(MarketConcentration marketConcentration) {

        List<MarketConcentration> marketConcentrationList = new ArrayList<>();
        List<Integer> collect = marketConcentrationList.stream().map(MarketConcentration::getId).collect(Collectors.toList());
        removeBatchByIds(collect);

        marketConcentration.setCreateBy(new String());
        marketConcentration.setCreateTime(new Date());
        marketConcentration.setUpdateBy(new String());
        marketConcentration.setUpdateTime(new Date());

        //上市集中度set
        getBaseMapper().updateMarketConcentration(marketConcentration);

    }
}

package com.arg.smart.web.statistics.service.impl;


import com.arg.smart.web.data.entity.CompanySales;
import com.arg.smart.web.data.mapper.CompanySalesMapper;
import com.arg.smart.web.data.service.CompanySalesService;
import com.arg.smart.web.statistics.entity.PurchasingHeat;
import com.arg.smart.web.statistics.mapper.PurchasingHeatMapper;
import com.arg.smart.web.statistics.service.PurchasingHeatService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.injector.methods.DeleteById;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 采购热度
 * @author cgli
 * @date: 2023-08-24
 * @version: V1.0.0
 */
@Service
public class PurchasingHeatServiceImpl extends ServiceImpl<PurchasingHeatMapper, PurchasingHeat> implements PurchasingHeatService {

    @Autowired
    private PurchasingHeatService purchasingHeatService;

    @Autowired
    private CompanySalesService companySalesService;



    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public void updatePurchasingHeat(PurchasingHeat purchasingHeat) {
        List<PurchasingHeat> purchasingHeatList = new ArrayList<>();
        List<Integer> collect = purchasingHeatList.stream().map(PurchasingHeat::getId).collect(Collectors.toList());
        removeBatchByIds(collect);

        purchasingHeat.setCreateBy(new String());
        purchasingHeat.setCreateTime(new Date());
        purchasingHeat.setUpdateBy(new String());
        purchasingHeat.setUpdateTime(new Date());

        //采购热度set
        getBaseMapper().updatePurchasingHeat(purchasingHeat);

    }
}

package com.arg.smart.web.cargo.service.impl;

import com.arg.smart.web.cargo.entity.ProductCirculationData;
import com.arg.smart.web.cargo.mapper.CarrierTransportationVolumeDataMapper;
import com.arg.smart.web.cargo.mapper.ProductCirculationDataMapper;
import com.arg.smart.web.cargo.req.ReqProductCirculationData;
import com.arg.smart.web.cargo.service.ProductCirculationDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.RoundingMode;
import java.time.LocalDate;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 货运表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
@Service
public class ProductCirculationDataServiceImpl extends ServiceImpl<ProductCirculationDataMapper, ProductCirculationData> implements ProductCirculationDataService {
    @Resource
    private ProductCirculationDataMapper productCirculationDataMapper ;
    @Resource
    private CarrierTransportationVolumeDataMapper carrierTransportationVolumeDataMapper;

    @Override
    public List<ProductCirculationData> selectListByCondition(ReqProductCirculationData reqProductCirculationData) {
        LambdaQueryWrapper<ProductCirculationData> queryWrapper = new LambdaQueryWrapper<>();
        Long orderId = reqProductCirculationData.getOrderId();
        Integer businessType = reqProductCirculationData.getBusinessType();
        String modeTransport = reqProductCirculationData.getModeTransport();
        if(orderId != null){
            queryWrapper.eq(ProductCirculationData::getOrderId,orderId);
        }
        if(businessType != null){
            queryWrapper.eq(ProductCirculationData::getBusinessType,businessType);
        }
        if(modeTransport != null){
            queryWrapper.like(ProductCirculationData::getModeTransport,modeTransport);
        }
        return this.list(queryWrapper);
    }
    // 根据模块类型字段，统计使用不同运输方式的占比
    @Override
    public Map<String, Double> selectPercentageByFlag(Integer flag) {
        QueryWrapper<ProductCirculationData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("mode_transport")
                    .eq("flag", flag);
        List<ProductCirculationData> productCirculationData = baseMapper.selectList(queryWrapper);
        List<String> p = productCirculationData.stream()
                .map(ProductCirculationData::getModeTransport)
                .collect(Collectors.toList());
        int total = p.size();
        Map<String, Double> map = new HashMap<>();
        while (!p.isEmpty()) {
            String model = p.get(0);
            List<String> result = p.stream()
                    .filter(model2 -> model2.equals(model))
                    .collect(Collectors.toList());
            double outcome = (double) result.size() / total;
            map.put(model, outcome);
            p.removeAll(result);
        }
        return map;
    }
    // 根据模块类型字段，统计运输均价
    @Override
    public BigDecimal selectAverageShippingPriceByFlag(Integer flag) {
        QueryWrapper<ProductCirculationData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("transportation_price")
                .eq("flag",flag);
        List<ProductCirculationData> productCirculationData = baseMapper.selectList(queryWrapper);
        BigDecimal total = new BigDecimal("0");
        total = productCirculationData.stream()
                .map(ProductCirculationData::getTransportationPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal length = new BigDecimal(productCirculationData.size());
        total = total.divide(length, 2, RoundingMode.HALF_UP);
        return total;
    }
        //承运商的数量，通过的到近段时间（如一年 或 一月）计算的不同订单到各渠道的数量
    @Override
    public Map<String, Integer> selectCompanyQuantity(Integer flag) {
        QueryWrapper<ProductCirculationData> queryWrapper = new QueryWrapper<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate oneYearAgo = currentDate.minusYears(1);
        queryWrapper.select("company_name")
                .eq("flag", flag)
                .ge("receiving_time", oneYearAgo);
        List<ProductCirculationData> productCirculationData = baseMapper.selectList(queryWrapper);
        Map<String, Integer> countMap = new HashMap<>();
        productCirculationData.stream()
                .map(ProductCirculationData::getCompanyName)
                .forEach(companyName -> countMap.merge(companyName, 1, Integer::sum));
        return countMap;
    }

}

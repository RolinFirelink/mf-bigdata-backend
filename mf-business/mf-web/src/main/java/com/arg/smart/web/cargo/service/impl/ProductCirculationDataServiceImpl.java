package com.arg.smart.web.cargo.service.impl;

import com.arg.smart.web.cargo.entity.CarrierTransportationVolumeData;
import com.arg.smart.web.cargo.entity.vo.CarrierTransportationVolumeDataList;
import com.arg.smart.web.cargo.entity.ProductCirculationData;
import com.arg.smart.web.cargo.mapper.CarrierTransportationVolumeDataMapper;
import com.arg.smart.web.cargo.mapper.ProductCirculationDataMapper;
import com.arg.smart.web.cargo.req.ReqProductCirculationData;
import com.arg.smart.web.cargo.service.ProductCirculationDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.time.LocalDateTime;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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


    /**
     * 计算不同订单到各个销售渠道的占比
     * @param flag
     * @return
     */
    @Override
    public Map<String, Double> selectChannelByFlag(int flag) {

        QueryWrapper<ProductCirculationData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("business_type")
                .eq("flag",flag);
        List<String> p = baseMapper.selectList(queryWrapper)
                .stream()
                .map(ProductCirculationData::getBusinessType)
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
            result.clear();
        }

        return map;
    }
}

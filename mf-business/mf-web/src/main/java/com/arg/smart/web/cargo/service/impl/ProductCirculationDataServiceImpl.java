package com.arg.smart.web.cargo.service.impl;

import com.arg.smart.web.cargo.entity.ProductCirculationData;
import com.arg.smart.web.cargo.mapper.ProductCirculationDataMapper;
import com.arg.smart.web.cargo.req.ReqProductCirculationData;
import com.arg.smart.web.cargo.service.ProductCirculationDataService;
import com.arg.smart.web.company.entity.Company;
import com.arg.smart.web.company.req.ReqCompany;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @description: 货运表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
@Service
public class ProductCirculationDataServiceImpl extends ServiceImpl<ProductCirculationDataMapper, ProductCirculationData> implements ProductCirculationDataService {

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
}

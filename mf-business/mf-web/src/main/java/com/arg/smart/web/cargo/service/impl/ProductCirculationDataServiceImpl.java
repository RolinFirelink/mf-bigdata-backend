package com.arg.smart.web.cargo.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.cargo.entity.ProductCirculationData;
import com.arg.smart.web.cargo.mapper.ProductCirculationDataMapper;
import com.arg.smart.web.cargo.req.ReqProductCirculationData;
import com.arg.smart.web.cargo.service.ProductCirculationDataService;
import com.arg.smart.web.company.entity.Company;
import com.arg.smart.web.company.mapper.CompanyMapper;
import com.arg.smart.web.company.req.ReqCompany;
import com.arg.smart.web.company.service.CompanyService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;
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
    private CompanyMapper companyMapper;

    @Override
    public PageResult<ProductCirculationData> selectListByCondition(ReqProductCirculationData reqProductCirculationData) {
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
        List<ProductCirculationData> list = this.list(queryWrapper);
        PageResult<ProductCirculationData> pageResult = new PageResult<>(list);
        List<ProductCirculationData> collect = list.stream().peek(item -> {
            item.setCompanyName(companyMapper.getNameById(item.getCompanyId()));
        }).collect(Collectors.toList());
        pageResult.setList(collect);
        return pageResult;
    }
}

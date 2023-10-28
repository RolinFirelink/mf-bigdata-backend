package com.arg.smart.web.data.service.impl;

import com.arg.smart.web.data.entity.CompanyProduct;
import com.arg.smart.web.data.entity.vo.AvgProductValue;
import com.arg.smart.web.data.mapper.CompanyProductMapper;
import com.arg.smart.web.data.service.CompanyProductService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

public class CompanyProductServiceImpl extends ServiceImpl<CompanyProductMapper, CompanyProduct> implements CompanyProductService {

    @Override
    public List<CompanyProduct> selectCompanyProductList(Integer flag) {
        LambdaQueryWrapper<CompanyProduct> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(CompanyProduct::getFlag,flag);
        return baseMapper.selectList(lambdaQueryWrapper);
    }

    @Override
    public List<AvgProductValue> companyProductValue(String productName) {
        return baseMapper.companyProductValue(productName);
    }
}

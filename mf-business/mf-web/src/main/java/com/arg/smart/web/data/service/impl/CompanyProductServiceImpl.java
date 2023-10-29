package com.arg.smart.web.data.service.impl;

import com.arg.smart.web.data.entity.CompanyProduct;
import com.arg.smart.web.data.entity.vo.AvgProductValue;
import com.arg.smart.web.data.mapper.CompanyProductMapper;
import com.arg.smart.web.data.req.ReqCompanyProduct;
import com.arg.smart.web.data.service.CompanyProductService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
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

    @Override
    public List<CompanyProduct> list(ReqCompanyProduct reqCompanyProduct) {
        Integer flag = reqCompanyProduct.getFlag();
        String companyName = reqCompanyProduct.getCompanyName();
        String product = reqCompanyProduct.getProduct();
        LambdaQueryWrapper<CompanyProduct> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(flag != null,CompanyProduct::getFlag,flag);
        queryWrapper.like(companyName != null,CompanyProduct::getCompanyName,companyName);
        queryWrapper.like(product != null,CompanyProduct::getProductName,product);
        return this.list(queryWrapper);
    }
}


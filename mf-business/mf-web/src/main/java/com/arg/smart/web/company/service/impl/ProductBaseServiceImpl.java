package com.arg.smart.web.company.service.impl;

import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.mapper.ProductBaseMapper;
import com.arg.smart.web.company.service.ProductBaseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @author lwy
 * @description: 产品基地
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Service
public class ProductBaseServiceImpl extends ServiceImpl<ProductBaseMapper, ProductBase> implements ProductBaseService {

    @Override
    public List<ProductBase> getOptions() {
        LambdaQueryWrapper<ProductBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ProductBase::getId,ProductBase::getCompanyName);
        return this.list(queryWrapper);
    }
}

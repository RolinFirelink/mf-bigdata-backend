package com.arg.smart.web.company.service.impl;

import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.mapper.ProductBaseMapper;
import com.arg.smart.web.company.req.ReqProductBase;
import com.arg.smart.web.company.service.ProductBaseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

/**
 * @author lwy
 * @description: 产品基地
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Service
@Slf4j
public class ProductBaseServiceImpl extends ServiceImpl<ProductBaseMapper, ProductBase> implements ProductBaseService {

    @Override
    public List<ProductBase> getOptions() {
        LambdaQueryWrapper<ProductBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ProductBase::getId,ProductBase::getBaseName);
        return this.list(queryWrapper);
    }

    @Override
    public List<ProductBase> list(ReqProductBase reqProductBase) {
        QueryWrapper<ProductBase> companyQueryWrapper = new QueryWrapper<>();
        String baseName = reqProductBase.getBaseName();
        LambdaQueryWrapper<ProductBase> queryWrapper = new LambdaQueryWrapper<>();
        if (baseName != null) {
            queryWrapper.like(ProductBase::getBaseName, baseName);
        }
        Integer maxArea = reqProductBase.getMaxArea();
        if(maxArea != null){
            queryWrapper.le(ProductBase::getArea,maxArea);
        }
        Integer minArea = reqProductBase.getMinArea();
        if(minArea != null){
            queryWrapper.ge(ProductBase::getArea,minArea);
        }
        Integer attestation = reqProductBase.getAttestation();
        if(attestation != null){
            queryWrapper.like(ProductBase::getAttestation,attestation);
        }
        Integer flag = reqProductBase.getFlag();
        if(flag != null) {
            queryWrapper.eq(ProductBase::getFlag, flag);
        }
        if(baseName != null){
            companyQueryWrapper.like("base_name",baseName);
        }
        return this.list(companyQueryWrapper);
    }
}

package com.arg.smart.web.company.service.impl;

import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.mapper.ProductBaseMapper;
import com.arg.smart.web.company.req.ReqProductBase;
import com.arg.smart.web.company.service.ProductBaseService;
import com.arg.smart.web.company.vo.ProductBaseVO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import java.util.stream.Collectors;

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
        queryWrapper.select(ProductBase::getId, ProductBase::getBaseName);
        return this.list(queryWrapper);
    }

    @Override
    public List<ProductBase> list(ReqProductBase reqProductBase) {
        LambdaQueryWrapper<ProductBase> queryWrapper = new LambdaQueryWrapper<>();
        String baseName = reqProductBase.getBaseName();
        if (baseName != null) {
            queryWrapper.like(ProductBase::getBaseName, baseName);
        }
        Integer maxArea = reqProductBase.getMaxArea();
        if (maxArea != null) {
            queryWrapper.le(ProductBase::getArea, maxArea);
        }
        Integer minArea = reqProductBase.getMinArea();
        if (minArea != null) {
            queryWrapper.ge(ProductBase::getArea, minArea);
        }
        Integer attestation = reqProductBase.getAttestation();
        if (attestation != null) {
            queryWrapper.like(ProductBase::getAttestation, attestation);
        }
        Integer flag = reqProductBase.getFlag();
        if (flag != null) {
            queryWrapper.eq(ProductBase::getFlag, flag);
        }
        queryWrapper.orderByDesc(ProductBase::getWebsiteAddress);
        return this.list(queryWrapper);
    }




    public List<ProductBaseVO> getProductBaseInfo(ReqProductBase reqProductBase) {
        QueryWrapper<ProductBase> wrapper = new QueryWrapper<>();
        Integer flag = reqProductBase.getFlag();
        wrapper.eq("flag", flag);
        List<ProductBase> list = baseMapper.selectList(wrapper);
        return list.stream().map(productBase->{
            ProductBaseVO productBaseVO = new ProductBaseVO();
            // 设置其他属性
            productBaseVO.setBaseName(productBase.getBaseName());
            productBaseVO.setIphoneNumber(productBase.getContactPhone());
            productBaseVO.setCity(productBase.getCity());
            productBaseVO.setAnnualOutput(productBase.getAnnualOutput());
            productBaseVO.setMainProduct(productBase.getMainProduct());
            productBaseVO.setLat(productBase.getLat());
            productBaseVO.setLng(productBase.getLng());
            productBaseVO.setOutputUnit(productBase.getOutputUnit());
            productBaseVO.setRegion(productBase.getRegion());
            return productBaseVO;
        }).collect(Collectors.toList());
    }

}


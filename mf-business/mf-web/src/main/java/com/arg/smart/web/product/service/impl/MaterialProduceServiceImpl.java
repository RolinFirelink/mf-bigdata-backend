package com.arg.smart.web.product.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.company.mapper.CompanyMapper;
import com.arg.smart.web.company.mapper.ProductBaseMapper;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.arg.smart.web.product.entity.report.MaterialProduceWithProduceBase;
import com.arg.smart.web.product.entity.report.MaterialProduceWithYear;
import com.arg.smart.web.product.mapper.MaterialProduceMapper;
import com.arg.smart.web.product.req.ReqMaterialProduce;
import com.arg.smart.web.product.service.MaterialProduceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 产品生产表
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Service
public class MaterialProduceServiceImpl extends ServiceImpl<MaterialProduceMapper, MaterialProduce> implements MaterialProduceService {

    @Resource
    private CompanyMapper companyMapper;

    @Resource
    private ProductBaseMapper productBaseMapper;

    @Override
    public PageResult<MaterialProduce> list(ReqMaterialProduce reqMaterialProduce) {
        LambdaQueryWrapper<MaterialProduce> queryWrapper = new LambdaQueryWrapper<>();
        String name = reqMaterialProduce.getName();
        if (name != null) {
            queryWrapper.like(MaterialProduce::getName, name);
        }
        List<MaterialProduce> list = this.list(queryWrapper);
        PageResult<MaterialProduce> pageResult = new PageResult<>(list);
        List<MaterialProduce> collect = list.stream().peek(item -> {
            //设置公司名和基地名
            item.setCompanyName(companyMapper.getNameById(item.getCompanyId()));
            item.setProductBaseName(productBaseMapper.getNameById(item.getBaseId()));
        }).collect(Collectors.toList());
        pageResult.setList(collect);
        return pageResult;
    }

    @Override
    public List<MaterialProduceWithYear> getMaterialProductWithYears(Integer flag) {
        //根据年份group，生产规模和预计上市产量的总和
        return baseMapper.getMaterialProductWithYears(flag);
    }

    @Override
    public List<MaterialProduceWithProduceBase> getMaterialProduceWithProduceBase(Integer flag) {
        //根据产品基地ID聚合，生产规模和预计上市产量综合
        List<MaterialProduceWithProduceBase> materialProduceWithProduceBase = baseMapper.getMaterialProduceWithProduceBase(flag);
        return materialProduceWithProduceBase.stream().peek(item -> {
            item.setProduceBaseName(productBaseMapper.getNameById(item.getBaseId()));
        }).collect(Collectors.toList());
    }
}

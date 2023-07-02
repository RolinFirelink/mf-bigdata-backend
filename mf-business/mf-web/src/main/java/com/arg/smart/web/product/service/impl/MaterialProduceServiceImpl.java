package com.arg.smart.web.product.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.company.mapper.CompanyMapper;
import com.arg.smart.web.company.mapper.ProductBaseMapper;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.arg.smart.web.product.entity.report.*;
import com.arg.smart.web.product.mapper.MaterialProduceMapper;
import com.arg.smart.web.product.req.ReqMaterialProduce;
import com.arg.smart.web.product.service.MaterialProduceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
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

    @Override
    public List<MaterialProduceWithProduceBase> getByProduceBaseIdAndFlag(Integer flag) {
        return this.baseMapper.getByProduceIdAndFlag(flag);
    }

    @Override
    public void selectAndInsert() {
        List<MaterialProduceWithProduceBase> materialProduceWithProduceBase = this.baseMapper.selectProduce();
        //设置产品名字
        List<MaterialProduceWithProduceBase> insertData = materialProduceWithProduceBase.stream().peek(item -> {
            item.setProduceBaseName(productBaseMapper.getNameById(item.getBaseId()));
        }).collect(Collectors.toList());
        //插入统计表
        for (MaterialProduceWithProduceBase Data : insertData) {
            this.baseMapper.insertStatisticalResults(Data);
        }
    }

    @Override
    public MaterialProduceWithCity queryByCity(Integer flag) {
        List<CityWithScale> cityInfo = this.baseMapper.queryByCity(flag);
        MaterialProduceWithCity produceWithCity = this.baseMapper.queryOneByFlag(flag);
        if (produceWithCity != null) {
            produceWithCity.setCity(cityInfo);
        }
        return produceWithCity;
    }

    @Override
    public void selectScaleAndInsert() {
        //查询城市和其对应的规模数据
        List<CityWithScale> cityScale = this.baseMapper.selectScale();
        //插入
        String unit = null;
        int flag = -1;
        for (CityWithScale cityWithScale : cityScale) {
            if (flag != cityWithScale.getFlag()) {
                unit = this.baseMapper.getUnit(cityWithScale.getFlag());
                flag = cityWithScale.getFlag();
            }
            this.baseMapper.insertStatisticalTable(unit, cityWithScale);
        }
    }

    @Override
    public List<EstimateTimeAndMarket> queryByEstimateTime(Integer flag) {
        // 获取当前日期
        LocalDate now = LocalDate.now();
        //查询最近九个月的产品预计上市时间
        LocalDate queryTime = now.plusMonths(9);
        return this.baseMapper.selectByTime(flag, now, queryTime);
    }

    @Override
    public List<ProduceNameAndQuantity> getProduceQuantity(Integer flag) {
        List<ProduceNameAndQuantity> produceQuantity = this.baseMapper.getProduceQuantity(flag);
        BigDecimal allQuantity = BigDecimal.ZERO;
        //算总数
        for (int i = 0; i < produceQuantity.size(); i++) {
            allQuantity = allQuantity.add(BigDecimal.valueOf(produceQuantity.get(i).getQuantity()).setScale(2, RoundingMode.HALF_UP));
        }
        //算比例
        BigDecimal finalAllQuantity = allQuantity;
        return produceQuantity.stream().peek(item -> {
            item.setProportion("(" + new BigDecimal(item.getQuantity()).divide(finalAllQuantity,4,RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(100)).setScale(2,RoundingMode.HALF_UP) + "%)");
        }).collect(Collectors.toList());
    }

}

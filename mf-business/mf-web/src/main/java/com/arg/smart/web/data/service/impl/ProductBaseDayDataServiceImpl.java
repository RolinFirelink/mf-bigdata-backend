package com.arg.smart.web.data.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.service.ProductBaseService;
import com.arg.smart.web.data.entity.vo.BaseMarketResponseData;
import com.arg.smart.web.data.entity.vo.ProductMarketData;
import com.arg.smart.web.data.entity.vo.ProductSupply;
import com.arg.smart.web.data.entity.vo.SupplyHeatResponseData;
import com.arg.smart.web.data.req.ReqProductBaseDayData;
import com.arg.smart.web.data.entity.ProductBaseDayData;
import com.arg.smart.web.data.mapper.ProductBaseDayDataMapper;
import com.arg.smart.web.data.req.ReqProductBaseDayData;
import com.arg.smart.web.data.service.ProductBaseDayDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 产品基地每日数据
 * @date: 2023-07-18
 * @version: V1.0.0
 */
@Service
public class ProductBaseDayDataServiceImpl extends ServiceImpl<ProductBaseDayDataMapper, ProductBaseDayData> implements ProductBaseDayDataService {
    @Resource
    private ProductBaseService productBaseService;

    @Override
    public List<SupplyHeatResponseData> getSupplyHeat(ReqProductBaseDayData reqProductBaseDayData) {
        Integer flag = reqProductBaseDayData.getFlag();
        Date startTime = reqProductBaseDayData.getStartTime();
        Date endTime = reqProductBaseDayData.getEndTime();
        LambdaQueryWrapper<ProductBaseDayData> queryChainWrapper = new LambdaQueryWrapper<>();
        queryChainWrapper.eq(flag != null, ProductBaseDayData::getFlag, flag);
        queryChainWrapper.ge(startTime != null, ProductBaseDayData::getTime, startTime);
        queryChainWrapper.le(endTime != null, ProductBaseDayData::getTime, endTime);
        List<ProductBaseDayData> supplyHeat = this.list(queryChainWrapper);
        if (supplyHeat.size() == 0) {
            return new ArrayList<>();
        }
        List<SupplyHeatResponseData> list = new ArrayList<>();
        Map<Long, List<ProductBaseDayData>> map = supplyHeat.stream().collect(Collectors.groupingBy(ProductBaseDayData::getBaseId));
        List<ProductBase> productBases = productBaseService.listByIds(map.keySet());
        Map<Long, List<ProductBase>> baseMap = productBases.stream().collect(Collectors.groupingBy(ProductBase::getId));
        map.forEach((key, value) -> {
            SupplyHeatResponseData data = new SupplyHeatResponseData();
            List<ProductBase> productBases1 = baseMap.get(key);
            if (productBases1 != null) {
                data.setBaseName(productBases1.get(0).getBaseName());
                data.setLat(productBases1.get(0).getLat());
                data.setLng(productBases1.get(0).getLng());
                data.setCity(productBases1.get(0).getCity());
                data.setRegion(productBases1.get(0).getRegion());
            }
            data.setUnit(value.get(0).getUnit());
            List<ProductSupply> productSupplies = new ArrayList<>();
            for (ProductBaseDayData data1 : value) {
                ProductSupply productSupply = new ProductSupply(data1.getProduct(), data1.getSupply());
                productSupplies.add(productSupply);
            }
            data.setProductSupply(productSupplies);
            list.add(data);
        });
        return list;
    }

    @Override
    public List<BaseMarketResponseData> getMarketData(ReqProductBaseDayData reqProductBaseDayData) {
        List<BaseMarketResponseData> res = new ArrayList<>();
        Integer flag = reqProductBaseDayData.getFlag();
        Date startTime = reqProductBaseDayData.getStartTime();
        Date endTime = reqProductBaseDayData.getEndTime();
        QueryWrapper<ProductBaseDayData> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("flag", flag)
                .ge(startTime != null, "time", startTime)
                .le(endTime != null, "time", endTime);
        List<ProductBaseDayData> list = this.list(queryWrapper);
        if (list.size() == 0) {
            return new ArrayList<>();
        }
        Map<Long, List<ProductBaseDayData>> collect = list.stream().collect(Collectors.groupingBy(ProductBaseDayData::getBaseId));
        List<ProductBase> productBases = productBaseService.listByIds(collect.keySet());
        Map<Long, List<ProductBase>> baseMap = productBases.stream().collect(Collectors.groupingBy(ProductBase::getId));
        collect.forEach((baseId, value) -> {
            BaseMarketResponseData data = new BaseMarketResponseData();
            List<ProductBase> base = baseMap.get(baseId);
            if (base != null) {
                data.setBaseName(base.get(0).getBaseName());
                data.setCity(base.get(0).getCity());
                data.setRegion(base.get(0).getRegion());
                data.setLat(base.get(0).getLat());
                data.setLng(base.get(0).getLng());
            }
            data.setSalesUnit(value.get(0).getSalesUnit());
            data.setYieldUnit(value.get(0).getYieldUnit());
            List<ProductMarketData> productMarketDataList = new ArrayList<>();
            for (ProductBaseDayData productBaseDayData : value) {
                ProductMarketData productMarketData = new ProductMarketData();
                productMarketData.setSales(productBaseDayData.getSales());
                productMarketData.setYield(productBaseDayData.getYield());
                productMarketData.setProduct(productBaseDayData.getProduct());
                productMarketDataList.add(productMarketData);
            }
            data.setProductMarketData(productMarketDataList);
            res.add(data);
        });
        return res;
    }

    @Override
    public List<ProductBaseDayData> list(ReqProductBaseDayData reqProductBaseDayData) {
        LambdaQueryWrapper<ProductBaseDayData> queryWrapper = new LambdaQueryWrapper<>();
        //分页查询
        Integer flag = reqProductBaseDayData.getFlag();
        Date startTime = reqProductBaseDayData.getStartTime();
        Date endTime = reqProductBaseDayData.getEndTime();
        queryWrapper.eq(flag != null, ProductBaseDayData::getFlag, flag)
                .between(startTime != null && endTime != null, ProductBaseDayData::getTime, startTime, endTime);
        List<ProductBaseDayData> list = this.list(queryWrapper);
        list.stream().peek(item -> {
            String baseName = this.baseMapper.getBaseName(item.getBaseId());
            if (baseName != null) {
                item.setBaseName(baseName);
            }
        }).collect(Collectors.toList());
        return list;
    }

}

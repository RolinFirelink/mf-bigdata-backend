package com.arg.smart.web.data.service.impl;

import com.arg.smart.web.data.entity.vo.ProductSupply;
import com.arg.smart.web.data.entity.vo.SupplyHeatResponseData;
import com.arg.smart.web.data.req.ReqProductBaseDayData;
import com.arg.smart.web.data.entity.ProductBaseDayData;
import com.arg.smart.web.data.mapper.ProductBaseDayDataMapper;
import com.arg.smart.web.data.service.ProductBaseDayDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 产品基地每日数据
 * @author cgli
 * @date: 2023-07-18
 * @version: V1.0.0
 */
@Service
public class ProductBaseDayDataServiceImpl extends ServiceImpl<ProductBaseDayDataMapper, ProductBaseDayData> implements ProductBaseDayDataService {

    @Override
    public List<SupplyHeatResponseData> getSupplyHeat(ReqProductBaseDayData reqProductBaseDayData) {
        Integer flag = reqProductBaseDayData.getFlag();
        Date startTime = reqProductBaseDayData.getStartTime();
        Date endTime = reqProductBaseDayData.getEndTime();
        LambdaQueryWrapper<ProductBaseDayData> queryChainWrapper = new LambdaQueryWrapper<>();
        queryChainWrapper.eq(flag != null ,ProductBaseDayData::getFlag,flag);
        queryChainWrapper.ge(startTime!=null,ProductBaseDayData::getTime,startTime);
        queryChainWrapper.le(endTime != null,ProductBaseDayData::getTime,endTime);
        List<ProductBaseDayData> supplyHeat = this.list(queryChainWrapper);
        List<SupplyHeatResponseData> list = new ArrayList<>();
        Map<String, List<ProductBaseDayData>> map = supplyHeat.stream().collect(Collectors.groupingBy(ProductBaseDayData::getBaseName));
        map.forEach((key,value) -> {
            SupplyHeatResponseData data = new SupplyHeatResponseData();
            data.setBaseName(key);
            data.setLat(value.get(0).getLat());
            data.setLng(value.get(0).getLng());
            data.setUnit(value.get(0).getUnit());
            data.setCity(value.get(0).getCity());
            List<ProductSupply> productSupplies = new ArrayList<>();
            for (ProductBaseDayData data1 : value){
                ProductSupply productSupply = new ProductSupply(data1.getProduct(), data1.getSupply());
                productSupplies.add(productSupply);
            }
            data.setProductSupply(productSupplies);
            list.add(data);
        });
        return list;
    }

}

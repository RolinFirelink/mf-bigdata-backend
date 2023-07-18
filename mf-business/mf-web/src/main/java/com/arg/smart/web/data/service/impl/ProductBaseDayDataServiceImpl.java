package com.arg.smart.web.data.service.impl;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.data.entity.ProductBaseDayData;
import com.arg.smart.web.data.entity.vo.ProductSupply;
import com.arg.smart.web.data.entity.vo.SupplyHeatData;
import com.arg.smart.web.data.entity.vo.SupplyHeatReponseData;
import com.arg.smart.web.data.mapper.ProductBaseDayDataMapper;
import com.arg.smart.web.data.req.ReqProductBaseDayData;
import com.arg.smart.web.data.service.ProductBaseDayDataService;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.lang.reflect.Array;
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

    @Resource
    private ProductBaseDayDataMapper productBaseDayDataMapper;

    @Override
    public Result<List<SupplyHeatReponseData>> getSupplyHeat(Integer flag, ReqProductBaseDayData reqProductBaseDayData) {
        List<ProductBaseDayData> supplyHeat = productBaseDayDataMapper.getSupplyHeat(flag, reqProductBaseDayData.getStartTime(), reqProductBaseDayData.getEndTime());
        List<SupplyHeatReponseData> list = new ArrayList<>();
        Map<String, List<ProductBaseDayData>> map = supplyHeat.stream().collect(Collectors.groupingBy(ProductBaseDayData::getBaseName));
        map.forEach((key,value) -> {
            SupplyHeatReponseData data = new SupplyHeatReponseData();
            data.setBaseName(key);
            data.setLat(value.get(0).getLat());
            data.setLng(value.get(0).getLng());
            data.setUnit(value.get(0).getUnit());
            List<ProductSupply> productSupplies = new ArrayList<>();
            for (ProductBaseDayData data1 : value){
                ProductSupply productSupply = new ProductSupply(data1.getProduct(), data1.getSupply());
                productSupplies.add(productSupply);
            }
            data.setProductSupply(productSupplies);
            data.setCity(key.substring(0,key.indexOf("市") + 1));
            list.add(data);
        });
        return Result.ok(list);
    }
}

package com.arg.smart.web.docking.service.impl;

import com.arg.smart.web.docking.entity.ProduceData;
import com.arg.smart.web.docking.entity.ProductPriceData;
import com.arg.smart.web.docking.mapper.ProductPriceDataMapper;
import com.arg.smart.web.docking.req.ReqProductPriceData;
import com.arg.smart.web.docking.service.ProductPriceDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;

/**
 * @description: 惠农网产品价格
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Service
public class ProductPriceDataServiceImpl extends ServiceImpl<ProductPriceDataMapper, ProductPriceData> implements ProductPriceDataService {

    @Override
    public List<ProductPriceData> list(ReqProductPriceData reqProductPriceData) {
        LambdaQueryWrapper<ProductPriceData> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer flag = reqProductPriceData.getFlag();
        Date startDate = reqProductPriceData.getStartDate();
        Date endDate = reqProductPriceData.getEndDate();
        String region = reqProductPriceData.getRegion();
        String product = reqProductPriceData.getProduct();
        lambdaQueryWrapper.eq(flag != null,ProductPriceData::getFlag,flag);
        lambdaQueryWrapper.ge(startDate != null,ProductPriceData::getCollectdate,startDate);
        lambdaQueryWrapper.le(endDate != null,ProductPriceData::getCollectdate,endDate);
        lambdaQueryWrapper.like(region != null,ProductPriceData::getRegion,region);
        lambdaQueryWrapper.like(product != null,ProductPriceData::getProduct,product);
        return this.list(lambdaQueryWrapper);
    }
}

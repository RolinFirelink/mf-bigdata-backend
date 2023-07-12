package com.arg.smart.web.price.service.impl;


import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.price.entity.ProductMarketPrice;
import com.arg.smart.web.price.mapper.ProductMarketPriceMapper;
import com.arg.smart.web.price.req.ReqProductMarketPrice;
import com.arg.smart.web.price.service.ProductMarketPriceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author cgli
 * @description: 产品批发价格表
 * @date: 2023-07-12
 * @version: V1.0.0
 */
@Service
public class ProductMarketPriceServiceImpl extends ServiceImpl<ProductMarketPriceMapper, ProductMarketPrice> implements ProductMarketPriceService {

    @Override
    public PageResult<ProductMarketPrice> list(ReqProductMarketPrice reqProductMarketPrice) {
        LambdaQueryWrapper<ProductMarketPrice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(reqProductMarketPrice.getMarket() != null, ProductMarketPrice::getMarket, reqProductMarketPrice.getMarket())
                .eq(reqProductMarketPrice.getFlag() != null, ProductMarketPrice::getFlag, reqProductMarketPrice.getFlag());
        if (reqProductMarketPrice.getStartTime() != null && reqProductMarketPrice.getEndTime() != null) {
            queryWrapper.between(ProductMarketPrice::getRecordDate, reqProductMarketPrice.getStartTime(), reqProductMarketPrice.getEndTime());
        }
        return new PageResult<>(this.list(queryWrapper));
    }
}

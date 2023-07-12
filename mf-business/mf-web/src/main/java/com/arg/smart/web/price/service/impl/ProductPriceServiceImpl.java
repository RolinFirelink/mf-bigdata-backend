package com.arg.smart.web.price.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.price.entity.ProductMarketPrice;
import com.arg.smart.web.price.entity.ProductPrice;
import com.arg.smart.web.price.mapper.ProductPriceMapper;
import com.arg.smart.web.price.req.ReqProductPrice;
import com.arg.smart.web.price.service.ProductPriceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-12
 * @version: V1.0.0
 */
@Service
public class ProductPriceServiceImpl extends ServiceImpl<ProductPriceMapper, ProductPrice> implements ProductPriceService {

    @Override
    public PageResult<ProductPrice> list(ReqProductPrice reqProductPrice) {
        LambdaQueryWrapper<ProductPrice> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(reqProductPrice.getRegion() != null, ProductPrice::getRegion, reqProductPrice.getRegion())
                .eq(reqProductPrice.getFlag() != null, ProductPrice::getFlag, reqProductPrice.getFlag());
        if (reqProductPrice.getStartTime() != null && reqProductPrice.getEndTime() != null) {
            queryWrapper.between(ProductPrice::getTime, reqProductPrice.getStartTime(), reqProductPrice.getEndTime());
        }
        return new PageResult<>(this.list(queryWrapper));
    }
}

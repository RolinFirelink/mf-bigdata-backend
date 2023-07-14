package com.arg.smart.web.product.service.impl;

import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.entity.vo.PriceTemp;
import com.arg.smart.web.product.mapper.ProductPriceMapper;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.arg.smart.web.product.service.ProductPriceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 产品价格表
 * @date: 2023-07-01
 * @version: V1.0.0
 */
@Service
public class ProductPriceServiceImpl extends ServiceImpl<ProductPriceMapper, ProductPrice> implements ProductPriceService {
    @Override
    public List<ProductPrice> queryList(ReqProductPrice reqProductPrice) {
        LambdaQueryWrapper<ProductPrice> queryWrapper = new LambdaQueryWrapper<>();
        Integer flag = reqProductPrice.getFlag();
        LocalDate startTime = reqProductPrice.getStartTime();
        LocalDate endTime = reqProductPrice.getEndTime();
        String product = reqProductPrice.getProduct();
        String region = reqProductPrice.getRegion();
        if (flag != null) {
            queryWrapper.eq(ProductPrice::getFlag, flag);
        }
        if (product != null) {
            queryWrapper.like(ProductPrice::getProduct, product);
        }
        if (region != null) {
            queryWrapper.like(ProductPrice::getRegion, region);
        }
        if (startTime != null) {
            queryWrapper.gt(ProductPrice::getTime, startTime);
        }
        if (endTime != null) {
            queryWrapper.lt(ProductPrice::getTime, endTime);
        }
        return list(queryWrapper);
    }

    @Override
    public List<PriceTemp> getPriceTemp() {
        List<ProductPrice> list = baseMapper.getMaxTimePrice();
        return list.stream().map(item -> {
            BigDecimal price = item.getPrice();
            //上次的价格
            BigDecimal lastPrice = price.divide(item.getLifting().divide(new BigDecimal(100)).add(new BigDecimal(1)), 2, BigDecimal.ROUND_DOWN);
            //改变的价格
            BigDecimal changePrice = price.subtract(lastPrice);
            //指数
            Integer temp = price.divide(lastPrice, 3, BigDecimal.ROUND_DOWN).multiply(new BigDecimal(100)).intValue();
            return new PriceTemp(item.getFlag(), temp, changePrice, item.getUnit());
        }).collect(Collectors.toList());
    }

    @Override
    public List<String> regionList() {
        return baseMapper.regionList();
    }
}

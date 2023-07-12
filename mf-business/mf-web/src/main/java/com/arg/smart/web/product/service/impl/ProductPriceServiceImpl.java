package com.arg.smart.web.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.entity.vo.AvgPriceVO;
import com.arg.smart.web.product.mapper.ProductPriceMapper;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.arg.smart.web.product.service.ProductPriceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import netscape.javascript.JSObject;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.time.LocalDate;
import java.util.List;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-01
 * @version: V1.0.0
 */
@Service
public class ProductPriceServiceImpl extends ServiceImpl<ProductPriceMapper, ProductPrice> implements ProductPriceService {
    @Override
    public List<ProductPrice> queryList(ReqProductPrice reqProductPrice) {
        LambdaQueryWrapper<ProductPrice> queryWrapper = new LambdaQueryWrapper<>();
        Integer flag = reqProductPrice.getFlag();
        String time = reqProductPrice.getTime();
        String name = reqProductPrice.getName();
        String region = reqProductPrice.getRegion();
        if(flag != null){
            queryWrapper.eq(ProductPrice::getFlag,flag);
        }
        if(name != null){
            queryWrapper.like(ProductPrice::getProduct,name);
        }
        if(region != null){
            queryWrapper.like(ProductPrice::getRegion,region);
        }
        if(time != null){
            queryWrapper.lt(ProductPrice::getTime,time);
        }
        return list(queryWrapper);
    }

    @
    Override
    public  List<AvgPriceVO> selectAvgPriceOfDate(LocalDate startTime, LocalDate endTime){
        return baseMapper.selectAvgPriceOfDate(startTime.toString(), endTime.toString());
    }

    @Override
    public List<AvgPriceVO> selectAvgPriceOfMonth(LocalDate startTime, LocalDate endTime) {
        return baseMapper.selectAvgPriceOfMonth(startTime.toString(), endTime.toString());
    }
}

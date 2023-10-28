package com.arg.smart.web.data.service.impl;

import com.arg.smart.web.data.entity.ProductBaseDayData;
import com.arg.smart.web.data.entity.ProductValue;
import com.arg.smart.web.data.entity.vo.ProductValueTotal;
import com.arg.smart.web.data.mapper.ProductValueMapper;
import com.arg.smart.web.data.service.ProductValueService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

public class ProductValueServiceImpl extends ServiceImpl<ProductValueMapper, ProductValue> implements ProductValueService {
    @Override
    public List<ProductValueTotal> selectProductValueTotal(Integer flag,Integer date) {

        List<ProductValueTotal> productValueTotals=baseMapper.selectProductValueTotal(flag,date);

        return productValueTotals;
    }

    @Override
    public List<ProductValue> selectProductValue(Integer flag,String city) {
        QueryWrapper<ProductValue> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("id,flag,city,up_date,product_name,product_value,product_scale,region,date")
                .eq("flag",flag)
                .groupBy("city");
        return list(queryWrapper);
    }
}

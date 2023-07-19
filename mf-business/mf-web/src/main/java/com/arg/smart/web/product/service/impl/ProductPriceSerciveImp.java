package com.arg.smart.web.product.service.impl;


import com.arg.smart.web.product.entity.vo.ProductPrice;
import com.arg.smart.web.product.mapper.ProductPriceMapper;
import com.arg.smart.web.product.service.ProductPriceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.*;

@Service
public class ProductPriceSerciveImp /*extends ServiceImpl<ProductPriceMapper, ProductPrice> implements ProductPriceService*/ {
     /*   @Resource
        private ProductPriceMapper productPriceMapper;

    @Override
    public List<ProductPrice> selectPriceByDate(int flag, LocalDate startTime , LocalDate endTime) {
        QueryWrapper<ProductPrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("flag",flag)
                .between("time",startTime,endTime);
        List<ProductPrice> productPricesData = productPriceMapper.selectList(queryWrapper);
        return productPricesData;
    }*/

}

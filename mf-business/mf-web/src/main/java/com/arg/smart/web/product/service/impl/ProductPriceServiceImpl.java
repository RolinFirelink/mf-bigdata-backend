package com.arg.smart.web.product.service.impl;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.product.entity.report.PriceData;
import com.arg.smart.web.product.entity.temp;
import com.arg.smart.web.product.entity.temp2;
import com.arg.smart.web.product.entity.vo.ProductSupply;
import com.arg.smart.web.product.mapper.ProductPriceMapper;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.arg.smart.web.product.service.ProductPriceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.arg.smart.web.product.entity.ProductPrice;
import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-01
 * @version: V1.0.0
 */
@Service
public class ProductPriceServiceImpl extends ServiceImpl<ProductPriceMapper, com.arg.smart.web.product.entity.ProductPrice> implements ProductPriceService {

  /*  @Override
    public List<com.arg.smart.web.product.entity.ProductPrice> queryList(ReqProductPrice reqProductPrice) {
        LambdaQueryWrapper<com.arg.smart.web.product.entity.ProductPrice> queryWrapper = new LambdaQueryWrapper<>();
        Integer flag = reqProductPrice.getFlag();
        Date startTime = reqProductPrice.getStartTime();
        Date endTime = reqProductPrice.getEndTime();
        String name = reqProductPrice.getName();
        String region = reqProductPrice.getRegion();
        if(flag != null){
            queryWrapper.eq(com.arg.smart.web.product.entity.ProductPrice::getFlag,flag);
        }
        if(name != null){
            queryWrapper.like(com.arg.smart.web.product.entity.ProductPrice::getProduct,name);
        }
        if(region != null){
            queryWrapper.like(com.arg.smart.web.product.entity.ProductPrice::getRegion,region);
        }
        if(startTime != null){
            queryWrapper.gt(com.arg.smart.web.product.entity.ProductPrice::getTime,startTime);
        }
        if(endTime != null){
            queryWrapper.lt(com.arg.smart.web.product.entity.ProductPrice::getTime,endTime);
        }
        return list(queryWrapper);
    }*/

    /*@Override
    public List<ProductPrice> getPriceReportData(ReqProductPrice reqProductPrice) {
        return null;
    }*/

    /* @Override
     public List<PriceData> getPriceReportData(ReqProductPrice reqProductPrice) {
         return null;
     }*/
    /*@Override
    public List<com.arg.smart.web.product.entity.ProductPrice> selectPriceByDate(int flag, LocalDate startTime , LocalDate endTime) {
        QueryWrapper<com.arg.smart.web.product.entity.ProductPrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("flag",flag)
                .between("time",startTime,endTime);
        List<com.arg.smart.web.product.entity.ProductPrice> productPricesData = baseMapper.selectList(queryWrapper);
        return productPricesData;
    }
    @Override
    public List<com.arg.smart.web.product.entity.ProductPrice> selectPriceByDate(int flag, LocalDate startTime,LocalDate endTime) {
        QueryWrapper<com.arg.smart.web.product.entity.ProductPrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("flag", flag)
                .between("time", startTime, endTime);
        List<com.arg.smart.web.product.entity.ProductPrice> productPricesData = baseMapper.selectList(queryWrapper);

        return productPricesData;
    }*/
    @Override
    public List<ProductSupply> selectSupplyByFlag(Integer flag) {

        return baseMapper.selectSupplyByFlag(flag);
    }

    @Override
    public Integer temp() {
        List<temp> temps = baseMapper.selectAllLatLng();
        Integer results = 0;
        List<temp2> temp2s = baseMapper.selectBaseName();
        System.out.println(temp2s.size());
        System.out.println(temps.size());
       /* for (int i = 0 ; i<temps.size();i++){
             //results += baseMapper.updateLatLng(temps.get(i));
        }*/
        for(int i = 0 ;i<74;i++){
            if(temps.get(i).getMarket().equals(temp2s.get(i).getBaseName())){
                results++;
            }
        }
        System.out.println(results);
        //System.out.println(temps.size()+""+results);
        return results;
    }

}

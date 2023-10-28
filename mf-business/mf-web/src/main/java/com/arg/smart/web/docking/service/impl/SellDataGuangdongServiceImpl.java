package com.arg.smart.web.docking.service.impl;

import com.arg.smart.web.docking.entity.SellData;
import com.arg.smart.web.docking.entity.SellDataGuangdong;
import com.arg.smart.web.docking.mapper.SellDataGuangdongMapper;
import com.arg.smart.web.docking.req.ReqSellDataGuangdong;
import com.arg.smart.web.docking.service.SellDataGuangdongService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @description: 惠农网广东月销售数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Service
public class SellDataGuangdongServiceImpl extends ServiceImpl<SellDataGuangdongMapper, SellDataGuangdong> implements SellDataGuangdongService {

    @Override

    public List<SellDataGuangdong> list(ReqSellDataGuangdong reqSellDataGuangdong) {
        LambdaQueryWrapper<SellDataGuangdong> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer flag = reqSellDataGuangdong.getFlag();
        String startMonth = reqSellDataGuangdong.getStartMonth();
        String endMonth = reqSellDataGuangdong.getEndMonth();
        String buyProvinceName = reqSellDataGuangdong.getBuyProvinceName();
        lambdaQueryWrapper.eq(flag != null,SellDataGuangdong::getFlag,flag);
        lambdaQueryWrapper.ge(startMonth != null,SellDataGuangdong::getDate,startMonth);
        lambdaQueryWrapper.le(endMonth != null,SellDataGuangdong::getDate,endMonth);
        lambdaQueryWrapper.like(buyProvinceName != null,SellDataGuangdong::getBuyProvinceName,buyProvinceName);
        return this.list(lambdaQueryWrapper);
    }
}

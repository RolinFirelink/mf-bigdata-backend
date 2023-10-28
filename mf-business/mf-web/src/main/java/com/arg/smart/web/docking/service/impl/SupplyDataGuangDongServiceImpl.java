package com.arg.smart.web.docking.service.impl;

import com.arg.smart.web.docking.entity.SupplyData;
import com.arg.smart.web.docking.entity.SupplyDataGuangDong;
import com.arg.smart.web.docking.mapper.SupplyDataGuangDongMapper;
import com.arg.smart.web.docking.req.ReqSupplyDataGuangDong;
import com.arg.smart.web.docking.service.SupplyDataGuangDongService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @description: 惠农网广东月供应数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Service
public class SupplyDataGuangDongServiceImpl extends ServiceImpl<SupplyDataGuangDongMapper, SupplyDataGuangDong> implements SupplyDataGuangDongService {

    @Override
    public List<SupplyDataGuangDong> list(ReqSupplyDataGuangDong reqSupplyDataGuangDong) {

        LambdaQueryWrapper<SupplyDataGuangDong> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer flag = reqSupplyDataGuangDong.getFlag();
        String startMonth = reqSupplyDataGuangDong.getStartMonth();
        String endMonth = reqSupplyDataGuangDong.getEndMonth();
        lambdaQueryWrapper.eq(flag != null,SupplyDataGuangDong::getFlag,flag);
        lambdaQueryWrapper.ge(startMonth != null,SupplyDataGuangDong::getDate,startMonth);
        lambdaQueryWrapper.le(endMonth != null,SupplyDataGuangDong::getDate,endMonth);
        return this.list(lambdaQueryWrapper);
    }
}

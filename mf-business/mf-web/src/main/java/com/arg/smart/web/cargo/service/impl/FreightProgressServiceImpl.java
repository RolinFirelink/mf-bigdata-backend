package com.arg.smart.web.cargo.service.impl;

import com.arg.smart.web.cargo.entity.FreightProgress;
import com.arg.smart.web.cargo.mapper.FreightProgressMapper;
import com.arg.smart.web.cargo.req.ReqFreightProgress;
import com.arg.smart.web.cargo.service.FreightProgressService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @description: 货运进度表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
@Service
public class FreightProgressServiceImpl extends ServiceImpl<FreightProgressMapper, FreightProgress> implements FreightProgressService {


    @Override
    public List<FreightProgress> selectListByCondition(ReqFreightProgress reqFreightProgress) {
        LambdaQueryWrapper<FreightProgress> queryWrapper = new LambdaQueryWrapper<>();
        String transportState = reqFreightProgress.getTransportState();
        if(transportState != null){
            queryWrapper.eq(FreightProgress::getTransportState,transportState);
        }
        return this.list(queryWrapper);
    }

}

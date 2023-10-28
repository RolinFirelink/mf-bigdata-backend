package com.arg.smart.web.miniProgram.service.impl;

import com.arg.smart.web.miniProgram.entity.RotationChart;
import com.arg.smart.web.miniProgram.mapper.RotationChartMapper;
import com.arg.smart.web.miniProgram.req.ReqRotationChart;
import com.arg.smart.web.miniProgram.service.RotationChartService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author cgli
 * @description: 轮播图信息
 * @date: 2023-06-02
 * @version: V1.0.0
 */
@Service
public class RotationChartServiceImpl extends ServiceImpl<RotationChartMapper, RotationChart> implements RotationChartService {


    @Override
    public List<RotationChart> getSwiperList(ReqRotationChart reqRotationChart) {
        Integer position = reqRotationChart.getPosition();
        LambdaQueryWrapper<RotationChart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RotationChart::getStatus, 1).orderByAsc(RotationChart::getSort);
        if(position != null){
            queryWrapper.eq(RotationChart::getPosition,position);
        }
        queryWrapper.select(RotationChart::getImgUrl, RotationChart::getPath);
        return this.list(queryWrapper);
    }

    @Override
    public List<RotationChart> list(ReqRotationChart reqRotationChart) {
        LambdaQueryWrapper<RotationChart> queryWrapper = new LambdaQueryWrapper<>();
        Integer status = reqRotationChart.getStatus();
        Integer position = reqRotationChart.getPosition();
        queryWrapper.eq(status != null, RotationChart::getStatus, status);
        queryWrapper.eq(position != null,RotationChart::getPosition,position);
        return this.list(queryWrapper);
    }
}

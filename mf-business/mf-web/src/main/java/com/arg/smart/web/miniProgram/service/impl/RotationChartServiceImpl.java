package com.arg.smart.web.miniProgram.service.impl;

import com.arg.smart.web.miniProgram.entity.RotationChart;
import com.arg.smart.web.miniProgram.mapper.RotationChartMapper;
import com.arg.smart.web.miniProgram.service.RotationChartService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 轮播图信息
 * @author cgli
 * @date: 2023-06-02
 * @version: V1.0.0
 */
@Service
public class RotationChartServiceImpl extends ServiceImpl<RotationChartMapper, RotationChart> implements RotationChartService {


    @Override
    public List<RotationChart> getSwiperList() {
        LambdaQueryWrapper<RotationChart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(RotationChart::getStatus,1).orderByAsc(RotationChart::getSort);
        queryWrapper.select(RotationChart::getImgUrl,RotationChart::getPath);
        return this.list(queryWrapper);
    }
}

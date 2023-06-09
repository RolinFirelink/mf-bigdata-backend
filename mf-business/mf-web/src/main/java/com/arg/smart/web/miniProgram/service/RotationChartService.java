package com.arg.smart.web.miniProgram.service;

import com.arg.smart.web.miniProgram.entity.RotationChart;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 轮播图信息
 * @author cgli
 * @date: 2023-06-02
 * @version: V1.0.0
 */
public interface RotationChartService extends IService<RotationChart> {

    List<RotationChart> getSwiperList();
}

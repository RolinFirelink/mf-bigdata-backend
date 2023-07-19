package com.arg.smart.web.statistics.service;

import com.arg.smart.web.statistics.entity.CitySaleStatistics;
import com.arg.smart.web.statistics.req.ReqCitySaleStatistics;
import com.arg.smart.web.statistics.vo.CitySaleStatisticsVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 城市销售量表
 * @author cgli
 * @date: 2023-07-17
 * @version: V1.0.0
 */
public interface CitySaleStatisticsService extends IService<CitySaleStatistics> {

    List<CitySaleStatisticsVO> list(ReqCitySaleStatistics reqCitySaleStatistics);
}

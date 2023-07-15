package com.arg.smart.web.statistics.service;

import com.arg.smart.web.statistics.entity.ProvinceSaleStatistics;
import com.arg.smart.web.product.req.ReqProvinceSaleStatistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 省份销售数据
 * @author cgli
 * @date: 2023-07-15
 * @version: V1.0.0
 */
public interface ProvinceSaleStatisticsService extends IService<ProvinceSaleStatistics> {

    List<ProvinceSaleStatistics> list(ReqProvinceSaleStatistics reqProvinceSaleStatistics);
}

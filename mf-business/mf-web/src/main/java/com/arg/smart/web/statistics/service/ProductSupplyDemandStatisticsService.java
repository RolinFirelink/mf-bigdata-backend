package com.arg.smart.web.statistics.service;

import com.arg.smart.web.statistics.entity.ProductSupplyDemandStatistics;
import com.arg.smart.web.statistics.req.ReqProductSupplyDemandStatistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 产品供需统计表
 * @author cgli
 * @date: 2023-07-17
 * @version: V1.0.0
 */
public interface ProductSupplyDemandStatisticsService extends IService<ProductSupplyDemandStatistics> {

    List<ProductSupplyDemandStatistics> list(ReqProductSupplyDemandStatistics reqProductSupplyDemandStatistics);

    List<ProductSupplyDemandStatistics> pageList(ReqProductSupplyDemandStatistics reqProductSupplyDemandStatistics);
}

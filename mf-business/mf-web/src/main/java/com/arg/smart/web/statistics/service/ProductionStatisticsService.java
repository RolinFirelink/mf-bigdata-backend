package com.arg.smart.web.statistics.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.statistics.entity.ProductionStatistics;
import com.arg.smart.web.product.req.ReqProductionStatistics;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 生产统计
 * @author cgli
 * @date: 2023-07-15
 * @version: V1.0.0
 */
public interface ProductionStatisticsService extends IService<ProductionStatistics> {

    List<ProductionStatistics> list(ReqProductionStatistics reqProductionStatistics);

    PageResult<ProductionStatistics> listPage(ReqProductionStatistics reqProductionStatistics);
}

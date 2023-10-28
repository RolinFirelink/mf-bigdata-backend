package com.arg.smart.web.statistics.service;

import com.arg.smart.web.statistics.entity.ProductionData;
import com.arg.smart.web.statistics.req.ReqProductionData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 地区生产宏观数据
 * @author cgli
 * @date: 2023-10-19
 * @version: V1.0.0
 */
public interface ProductionDataService extends IService<ProductionData> {

    List<ProductionData> list(ReqProductionData reqProductionData);
}

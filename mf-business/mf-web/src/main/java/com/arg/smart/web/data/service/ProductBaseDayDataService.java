package com.arg.smart.web.data.service;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.data.entity.ProductBaseDayData;
import com.arg.smart.web.data.entity.vo.SupplyHeatData;
import com.arg.smart.web.data.entity.vo.SupplyHeatReponseData;
import com.arg.smart.web.data.req.ReqProductBaseDayData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 产品基地每日数据
 * @author cgli
 * @date: 2023-07-18
 * @version: V1.0.0
 */
public interface ProductBaseDayDataService extends IService<ProductBaseDayData> {

    Result<List<SupplyHeatReponseData>> getSupplyHeat(Integer flag, ReqProductBaseDayData reqProductBaseDayData);
}

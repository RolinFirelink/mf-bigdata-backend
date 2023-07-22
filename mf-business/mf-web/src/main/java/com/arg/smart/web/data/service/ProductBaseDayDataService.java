package com.arg.smart.web.data.service;

import com.arg.smart.web.data.entity.ProductBaseDayData;
import com.arg.smart.web.data.entity.vo.BaseMarketResponseData;
import com.arg.smart.web.data.entity.vo.ProductionScaleResponseData;
import com.arg.smart.web.data.entity.vo.SupplyHeatResponseData;
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
   List<SupplyHeatResponseData> getSupplyHeat(ReqProductBaseDayData reqProductBaseDayData);

   List<BaseMarketResponseData> getMarketData(ReqProductBaseDayData reqProductBaseDayData);
}

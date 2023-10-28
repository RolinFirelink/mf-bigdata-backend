package com.arg.smart.web.docking.service;

import com.arg.smart.web.docking.entity.MarketPriceData;
import com.arg.smart.web.docking.req.ReqMarketPriceData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 惠农网批发市场数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
public interface MarketPriceDataService extends IService<MarketPriceData> {

    List<MarketPriceData> list(ReqMarketPriceData reqMarketPrice);
}

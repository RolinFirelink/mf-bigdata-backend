package com.arg.smart.web.product.service;

import com.arg.smart.web.product.entity.MarketPrice;
import com.arg.smart.web.product.req.ReqMarketPrice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 批发市场价格
 * @author cgli
 * @date: 2023-07-09
 * @version: V1.0.0
 */
public interface MarketPriceService extends IService<MarketPrice> {

    List<MarketPrice> list(ReqMarketPrice reqMarketPrice);
}

package com.arg.smart.web.price.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.price.entity.ProductMarketPrice;
import com.arg.smart.web.price.req.ReqProductMarketPrice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 产品批发价格表
 * @author cgli
 * @date: 2023-07-12
 * @version: V1.0.0
 */
public interface ProductMarketPriceService extends IService<ProductMarketPrice> {

    PageResult<ProductMarketPrice> list(ReqProductMarketPrice reqPage);

}

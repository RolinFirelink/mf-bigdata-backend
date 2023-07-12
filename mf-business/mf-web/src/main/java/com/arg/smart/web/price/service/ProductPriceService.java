package com.arg.smart.web.price.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.price.entity.ProductPrice;
import com.arg.smart.web.price.req.ReqProductPrice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-12
 * @version: V1.0.0
 */
public interface ProductPriceService extends IService<ProductPrice> {

    PageResult<ProductPrice> list(ReqProductPrice reqProductPrice);
}

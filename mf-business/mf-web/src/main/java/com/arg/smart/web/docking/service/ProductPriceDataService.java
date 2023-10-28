package com.arg.smart.web.docking.service;

import com.arg.smart.web.docking.entity.ProductPriceData;
import com.arg.smart.web.docking.req.ReqProductPriceData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 惠农网产品价格
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
public interface ProductPriceDataService extends IService<ProductPriceData> {

    List<ProductPriceData> list(ReqProductPriceData reqProductPrice);
}

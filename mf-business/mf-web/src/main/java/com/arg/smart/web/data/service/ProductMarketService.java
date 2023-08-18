package com.arg.smart.web.data.service;

import com.arg.smart.web.data.entity.ProductMarket;
import com.arg.smart.web.data.req.ReqProductMarket;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 批发市场表
 * @author cgli
 * @date: 2023-08-18
 * @version: V1.0.0
 */
public interface ProductMarketService extends IService<ProductMarket> {

    List<ProductMarket> pageList(ReqProductMarket reqProductMarket);

    boolean updateProductMarketById(ProductMarket productMarket);

    boolean saveProductMarket(ProductMarket productMarket);
}

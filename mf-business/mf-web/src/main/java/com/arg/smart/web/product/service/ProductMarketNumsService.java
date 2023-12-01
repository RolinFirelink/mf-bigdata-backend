package com.arg.smart.web.product.service;

import com.arg.smart.web.product.entity.ProductMarketNums;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 批发市场采购量表
 * @author cgli
 * @date: 2023-07-30
 * @version: V1.0.0
 */
public interface ProductMarketNumsService extends IService<ProductMarketNums> {


    /**
     * 从惠农网采购大厅中爬取数据
     * @return
     */
    boolean saveByPurchase();
}

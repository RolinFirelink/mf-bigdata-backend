package com.arg.smart.web.product.service;

import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-01
 * @version: V1.0.0
 */
public interface ProductPriceService extends IService<ProductPrice> {

    List<ProductPrice> queryList(ReqProductPrice reqProductPrice);

    /**
     * 通过爬虫将惠农网信息添加到数据库中
     * @return
     */
    boolean cnhnbSave();
}

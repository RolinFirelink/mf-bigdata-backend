package com.arg.smart.web.product.service;


import com.arg.smart.web.product.entity.vo.ProductPrice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.time.LocalDate;
import java.util.List;

/**
 * @description: 产品价格表
 * @author Hanyuehong
 * @date: 2023-07-12
 * @version: V1.0.0
 */
public interface ProductPriceService extends IService<ProductPrice> {
    List<ProductPrice> selectPriceByDate (int flag , LocalDate startTime , LocalDate endTime);
}

package com.arg.smart.web.product.service;

import com.arg.smart.web.product.entity.ProductPrice;
//import com.arg.smart.web.product.entity.vo.ProductPrice;
import com.arg.smart.web.product.entity.temp;
import com.arg.smart.web.product.entity.vo.ProductSupply;
import com.baomidou.mybatisplus.extension.service.IService;
import java.time.LocalDate;
import com.arg.smart.web.product.entity.report.PriceData;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

/**
 * @description: 产品价格表
 * @author Hanyuehong
 * @date: 2023-07-12
 * @version: V1.0.0
 */
public interface ProductPriceService extends IService<ProductPrice> {
    //List<com.arg.smart.web.product.entity.ProductPrice> selectPriceByDate (int flag , LocalDate startTime , LocalDate endTime);

    //List<ProductPrice> queryList(ReqProductPrice reqProductPrice);

    //List<PriceData> getPriceReportData(ReqProductPrice reqProductPrice);

   // List<com.arg.smart.web.product.entity.ProductPrice> getPriceReportData(ReqProductPrice reqProductPrice);
    List<ProductSupply> selectSupplyByFlag(Integer flag);
    Integer temp();
}

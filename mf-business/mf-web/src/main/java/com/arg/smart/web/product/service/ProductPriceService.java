package com.arg.smart.web.product.service;

import com.alibaba.fastjson.JSONObject;
import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.entity.vo.AvgPriceVO;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.baomidou.mybatisplus.extension.service.IService;
import netscape.javascript.JSObject;

import java.time.LocalDate;
import java.util.List;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-01
 * @version: V1.0.0
 */
public interface ProductPriceService extends IService<ProductPrice> {

    List<ProductPrice> queryList(ReqProductPrice reqProductPrice);

    List<AvgPriceVO> selectAvgPriceOfDate(LocalDate startTime, LocalDate endTime);

    List<AvgPriceVO> selectAvgPriceOfMonth(LocalDate startTime, LocalDate endTime);
}

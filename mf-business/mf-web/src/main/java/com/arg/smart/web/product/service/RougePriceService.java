package com.arg.smart.web.product.service;

import com.arg.smart.web.product.entity.ProductPriceTrendData;
import com.arg.smart.web.product.entity.RougePrice;
import com.arg.smart.web.product.entity.vo.ProductPriceTrend;
import com.arg.smart.web.product.entity.vo.RougePriceVo;
import com.arg.smart.web.product.req.ReqRougePrice;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @description: 肉鸽价格表
 * @author cgli
 * @date: 2023-07-31
 * @version: V1.0.0
 */
public interface RougePriceService extends IService<RougePrice> {

    List<RougePriceVo> queryList(ReqRougePrice reqRougePrice);

    List<RougePriceVo> getTrend(ReqRougePrice reqRougePrice);

    List<ProductPriceTrend> getPriceTrend(ReqRougePrice reqRougePrice);
}

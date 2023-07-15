package com.arg.smart.web.product.service;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.entity.vo.AreaAvgPriceAndSales;
import com.arg.smart.web.product.entity.vo.PriceTemp;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;
import java.util.Map;

/**
 * @author cgli
 * @description: 产品价格表
 * @date: 2023-07-01
 * @version: V1.0.0
 */
public interface ProductPriceService extends IService<ProductPrice> {

    List<ProductPrice> queryList(ReqProductPrice reqProductPrice);

    List<PriceTemp> getPriceTemp();

    List<String> regionList();

    List<AreaAvgPriceAndSales> selectAvgPriceAndSales(Integer flag, String product);

    List<ProductPrice> publicTrend(ReqProductPrice reqProductPrice);
}

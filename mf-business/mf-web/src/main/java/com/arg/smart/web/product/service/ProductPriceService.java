package com.arg.smart.web.product.service;
import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.entity.ProductPriceTrendData;
import com.arg.smart.web.product.entity.vo.AreaAvgPriceAndSales;
import com.arg.smart.web.product.entity.vo.PriceTemp;
import com.arg.smart.web.product.entity.vo.ProductPriceVO;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;
import org.bouncycastle.cert.ocsp.Req;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author cgli
 * @description: 产品价格表
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

    List<PriceTemp> getPriceTemp(ReqProductPrice reqProductPrice);

    List<String> regionList();

    List<AreaAvgPriceAndSales> selectAvgPriceAndSales(Integer flag, String product);

    List<ProductPriceVO> publicTrend(ReqProductPrice reqProductPrice);

    List<ProductPriceTrendData> getProductPriceTrendData(ReqProductPrice reqProductPrice);

    //获取某日某地区最高价格和最低价格
    ProductPriceVO getDailyPriceInfo(ReqProductPrice reqProductPrice);

    List<ProductPrice> queryPage(ReqProductPrice reqProductPrice);

    List<ProductPriceTrendData> getProductPriceTrendDataForecast(ReqProductPrice reqProductPrice);

    List<ProductPrice> avgPrice(ReqProductPrice reqProductPrice);

    List<ProductPrice> getPublicTemp(List<Integer> flags,String region);

    BigDecimal getAvgPriceByMonthAndRegion(String provinceName, String month,Integer flag);

    BigDecimal getAvgPriceByTimeAndRegion(Date startTime, Date endTime, String province,Integer flag);
}

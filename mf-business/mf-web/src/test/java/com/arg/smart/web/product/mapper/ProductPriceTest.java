package com.arg.smart.web.product.mapper;

import com.arg.smart.web.MfWebApplication;
import com.arg.smart.web.product.entity.ProductPriceTrendData;
import com.arg.smart.web.product.req.ReqProductPrice;
import com.arg.smart.web.product.service.ProductPriceService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

@SpringBootTest(classes = MfWebApplication.class)
@RunWith(SpringJUnit4ClassRunner.class)
@Slf4j
public class ProductPriceTest {

    @Resource
    private ProductPriceService productPriceService;

    @Test
    public void test(){
        List<ProductPriceTrendData> productPriceTrendDataForecast = productPriceService.getProductPriceTrendDataForecast(new ReqProductPrice());
        log.info("{}",productPriceTrendDataForecast);
    }
}

package com.arg.smart.web.product.units;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.product.entity.ProductMarketPrice;
import com.arg.smart.web.product.entity.vo.ProductMarketPriceExcel;
import com.arg.smart.web.product.service.ProductMarketPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductMarketPriceDataListener extends AnalysisEventListener<ProductMarketPriceExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final ProductMarketPriceService productMarketPriceService;

    public ProductMarketPriceDataListener(ProductMarketPriceService productMarketPriceService) {
        this.productMarketPriceService = productMarketPriceService;
    }

    List<ProductMarketPrice> list = new ArrayList<>();
    
    @Override
    public void invoke(ProductMarketPriceExcel productMarketPriceExcel, AnalysisContext analysisContext) {
        log.info(productMarketPriceExcel.toString());
        ProductMarketPrice productMarketPrice = new ProductMarketPrice();
        BeanUtils.copyProperties(productMarketPriceExcel, productMarketPrice);
        // 对BigDecimal类型的字段进行数据转换
        BigDecimal bottomPrice = null;
        try {
            bottomPrice = new BigDecimal(productMarketPriceExcel.getBottomPrice().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        } catch (NumberFormatException e) {
            log.error("Invalid projectedProduction value: {}", productMarketPriceExcel.getBottomPrice());
            // 处理转换失败的情况，例如给字段设置默认值或抛出异常
        }
        productMarketPrice.setBottomPrice(bottomPrice);
        BigDecimal topPrice = null;
        try {
            topPrice = new BigDecimal(productMarketPriceExcel.getTopPrice().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        } catch (NumberFormatException e) {
            log.error("Invalid projectedProduction value: {}", productMarketPriceExcel.getTopPrice());
            // 处理转换失败的情况，例如给字段设置默认值或抛出异常
        }
        productMarketPrice.setTopPrice(topPrice);
        BigDecimal averagePrice = null;
        try {
            averagePrice = new BigDecimal(productMarketPriceExcel.getAveragePrice().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        } catch (NumberFormatException e) {
            log.error("Invalid projectedProduction value: {}", productMarketPriceExcel.getAveragePrice());
            // 处理转换失败的情况，例如给字段设置默认值或抛出异常
        }
        productMarketPrice.setAveragePrice(averagePrice);
        list.add(productMarketPrice);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        productMarketPriceService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

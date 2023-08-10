package com.arg.smart.web.product.units;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.entity.vo.ProductPriceExcel;
import com.arg.smart.web.product.service.ProductPriceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductPriceDataListener extends AnalysisEventListener<ProductPriceExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final ProductPriceService productPriceService;

    public ProductPriceDataListener(ProductPriceService productPriceService) {
        this.productPriceService = productPriceService;
    }

    List<ProductPrice> list = new ArrayList<>();
    
    @Override
    public void invoke(ProductPriceExcel productPriceExcel, AnalysisContext analysisContext) {
        log.info(productPriceExcel.toString());
        ProductPrice productPrice = new ProductPrice();
        BeanUtils.copyProperties(productPriceExcel, productPrice);
        // 对BigDecimal类型的字段进行数据转换
        BigDecimal price = null;
        try {
            price = new BigDecimal(productPriceExcel.getPrice().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        } catch (NumberFormatException e) {
            log.error("Invalid projectedProduction value: {}", productPriceExcel.getPrice());
            // 处理转换失败的情况，例如给字段设置默认值或抛出异常
        }
        productPrice.setPrice(price);
        BigDecimal lifting = null;
        try {
            lifting = new BigDecimal(productPriceExcel.getLifting().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        } catch (NumberFormatException e) {
            log.error("Invalid projectedProduction value: {}", productPriceExcel.getLifting());
            // 处理转换失败的情况，例如给字段设置默认值或抛出异常
        }
        productPrice.setLifting(lifting);
        list.add(productPrice);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        productPriceService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

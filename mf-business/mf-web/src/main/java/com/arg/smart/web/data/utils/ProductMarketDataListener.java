package com.arg.smart.web.data.utils;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.data.entity.ProductMarket;
import com.arg.smart.web.data.entity.vo.ProductMarketExcel;
import com.arg.smart.web.data.service.ProductMarketService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductMarketDataListener extends AnalysisEventListener<ProductMarketExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final ProductMarketService productMarketService;

    public ProductMarketDataListener(ProductMarketService productMarketService) {
        this.productMarketService = productMarketService;
    }

    List<ProductMarket> list = new ArrayList<>();

    @Override
    public void invoke(ProductMarketExcel productMarketExcel, AnalysisContext analysisContext) {
        log.info(productMarketExcel.toString());
        ProductMarket productMarket = new ProductMarket();
        BeanUtils.copyProperties(productMarketExcel, productMarket);
        // 对BigDecimal类型的字段进行数据转换
        BigDecimal lat = null;
        if (productMarketExcel.getLat() != null) {
            try {
                lat = new BigDecimal(productMarketExcel.getLat().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            } catch (NumberFormatException e) {
                log.error("Invalid Lat value: {}", productMarketExcel.getLat());
                // 处理转换失败的情况，例如给字段设置默认值或抛出异常
            }
        }
        productMarket.setLat(lat);
        BigDecimal lng = null;
        if (productMarketExcel.getLng() != null) {
            try {
                lng = new BigDecimal(productMarketExcel.getLng().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            } catch (NumberFormatException e) {
                log.error("Invalid price value: {}", productMarketExcel.getLng());
                // 处理转换失败的情况，例如给字段设置默认值或抛出异常
            }
        }
        productMarket.setLng(lng);
        list.add(productMarket);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        productMarketService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

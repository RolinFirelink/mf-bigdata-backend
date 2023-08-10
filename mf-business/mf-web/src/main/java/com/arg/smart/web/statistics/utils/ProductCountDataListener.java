package com.arg.smart.web.statistics.utils;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.statistics.entity.ProductCount;
import com.arg.smart.web.statistics.entity.vo.ProductCountExcel;
import com.arg.smart.web.statistics.service.ProductCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductCountDataListener extends AnalysisEventListener<ProductCountExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final ProductCountService productCountService;

    public ProductCountDataListener(ProductCountService productCountService) {
        this.productCountService = productCountService;
    }

    List<ProductCount> list = new ArrayList<>();
    
    @Override
    public void invoke(ProductCountExcel productCountExcel, AnalysisContext analysisContext) {
        log.info(productCountExcel.toString());
        ProductCount productCount = new ProductCount();
        BeanUtils.copyProperties(productCountExcel, productCount);
        // 对BigDecimal类型的字段进行数据转换
        BigDecimal productionScale = null;
        try {
            productionScale = new BigDecimal(productCountExcel.getProductionScale().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        } catch (NumberFormatException e) {
            log.error("Invalid productionScale value: {}", productCountExcel.getProductionScale());
            // 处理转换失败的情况，例如给字段设置默认值或抛出异常
        }
        productCount.setProductionScale(productionScale);
        list.add(productCount);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        productCountService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

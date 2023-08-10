package com.arg.smart.web.statistics.utils;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.statistics.entity.ProductSupplyDemandStatistics;
import com.arg.smart.web.statistics.entity.vo.ProductSupplyDemandStatisticsExcel;
import com.arg.smart.web.statistics.service.ProductSupplyDemandStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductSupplyDemandStatisticsDataListener extends AnalysisEventListener<ProductSupplyDemandStatisticsExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_SupplyDemandStatistics = 100;


    private final ProductSupplyDemandStatisticsService productSupplyDemandStatisticsService;

    public ProductSupplyDemandStatisticsDataListener(ProductSupplyDemandStatisticsService productSupplyDemandStatisticsService) {
        this.productSupplyDemandStatisticsService = productSupplyDemandStatisticsService;
    }

    List<ProductSupplyDemandStatistics> list = new ArrayList<>();
    
    @Override
    public void invoke(ProductSupplyDemandStatisticsExcel productSupplyDemandStatisticsExcel, AnalysisContext analysisContext) {
        log.info(productSupplyDemandStatisticsExcel.toString());
        ProductSupplyDemandStatistics productSupplyDemandStatistics = new ProductSupplyDemandStatistics();
        BeanUtils.copyProperties(productSupplyDemandStatisticsExcel, productSupplyDemandStatistics);
        list.add(productSupplyDemandStatistics);
        if (list.size() >= BATCH_SupplyDemandStatistics) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        productSupplyDemandStatisticsService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

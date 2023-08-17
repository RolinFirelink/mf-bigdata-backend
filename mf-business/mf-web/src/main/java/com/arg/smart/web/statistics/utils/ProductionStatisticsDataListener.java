package com.arg.smart.web.statistics.utils;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.statistics.entity.ProductionStatistics;
import com.arg.smart.web.statistics.entity.vo.ProductionStatisticsExcel;
import com.arg.smart.web.statistics.service.ProductionStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductionStatisticsDataListener extends AnalysisEventListener<ProductionStatisticsExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_ionStatistics = 100;


    private final ProductionStatisticsService productionStatisticsService;

    public ProductionStatisticsDataListener(ProductionStatisticsService productionStatisticsService) {
        this.productionStatisticsService = productionStatisticsService;
    }

    List<ProductionStatistics> list = new ArrayList<>();
    
    @Override
    public void invoke(ProductionStatisticsExcel productionStatisticsExcel, AnalysisContext analysisContext) {
        log.info(productionStatisticsExcel.toString());
        ProductionStatistics productionStatistics = new ProductionStatistics();
        BeanUtils.copyProperties(productionStatisticsExcel, productionStatistics);
        list.add(productionStatistics);
        if (list.size() >= BATCH_ionStatistics) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        productionStatisticsService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

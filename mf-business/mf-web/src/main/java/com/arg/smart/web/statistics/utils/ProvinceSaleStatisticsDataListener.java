package com.arg.smart.web.statistics.utils;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.statistics.entity.ProvinceSaleStatistics;
import com.arg.smart.web.statistics.entity.vo.ProvinceSaleStatisticsExcel;
import com.arg.smart.web.statistics.service.ProvinceSaleStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProvinceSaleStatisticsDataListener extends AnalysisEventListener<ProvinceSaleStatisticsExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final ProvinceSaleStatisticsService provinceSaleStatisticsService;

    public ProvinceSaleStatisticsDataListener(ProvinceSaleStatisticsService provinceSaleStatisticsService) {
        this.provinceSaleStatisticsService = provinceSaleStatisticsService;
    }

    List<ProvinceSaleStatistics> list = new ArrayList<>();
    
    @Override
    public void invoke(ProvinceSaleStatisticsExcel provinceSaleStatisticsExcel, AnalysisContext analysisContext) {
        log.info(provinceSaleStatisticsExcel.toString());
        ProvinceSaleStatistics provinceSaleStatistics = new ProvinceSaleStatistics();
        BeanUtils.copyProperties(provinceSaleStatisticsExcel, provinceSaleStatistics);
        // 对BigDecimal类型的字段进行数据转换
        BigDecimal averagePrice = null;
        try {
            averagePrice = new BigDecimal(provinceSaleStatisticsExcel.getAveragePrice().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        } catch (NumberFormatException e) {
            log.error("Invalid productionScale value: {}", provinceSaleStatisticsExcel.getAveragePrice());
            // 处理转换失败的情况，例如给字段设置默认值或抛出异常
        }
        provinceSaleStatistics.setAveragePrice(averagePrice);
        list.add(provinceSaleStatistics);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        provinceSaleStatisticsService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

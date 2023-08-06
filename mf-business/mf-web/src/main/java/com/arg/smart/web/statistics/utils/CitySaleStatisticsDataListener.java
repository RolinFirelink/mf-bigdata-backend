package com.arg.smart.web.statistics.utils;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.statistics.entity.CitySaleStatistics;
import com.arg.smart.web.statistics.entity.vo.CitySaleStatisticsExcel;
import com.arg.smart.web.statistics.service.CitySaleStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CitySaleStatisticsDataListener extends AnalysisEventListener<CitySaleStatisticsExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final CitySaleStatisticsService citySaleStatisticsService;

    public CitySaleStatisticsDataListener(CitySaleStatisticsService citySaleStatisticsService) {
        this.citySaleStatisticsService = citySaleStatisticsService;
    }

    List<CitySaleStatistics> list = new ArrayList<>();

    @Override
    public void invoke(CitySaleStatisticsExcel citySaleStatisticsExcel, AnalysisContext analysisContext) {
        log.info(citySaleStatisticsExcel.toString());
        CitySaleStatistics citySaleStatistics = new CitySaleStatistics();
        BeanUtils.copyProperties(citySaleStatisticsExcel, citySaleStatistics);
        list.add(citySaleStatistics);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        citySaleStatisticsService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

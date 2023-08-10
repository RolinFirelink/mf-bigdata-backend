package com.arg.smart.web.statistics.utils;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.statistics.entity.MarketStatistics;
import com.arg.smart.web.statistics.entity.vo.MarketStatisticsExcel;
import com.arg.smart.web.statistics.service.MarketStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class MarketStatisticsDataListener extends AnalysisEventListener<MarketStatisticsExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final MarketStatisticsService marketStatisticsService;

    public MarketStatisticsDataListener(MarketStatisticsService marketStatisticsService) {
        this.marketStatisticsService = marketStatisticsService;
    }

    List<MarketStatistics> list = new ArrayList<>();

    @Override
    public void invoke(MarketStatisticsExcel marketStatisticsExcel, AnalysisContext analysisContext) {
        log.info(marketStatisticsExcel.toString());
        MarketStatistics marketStatistics = new MarketStatistics();
        BeanUtils.copyProperties(marketStatisticsExcel, marketStatistics);
        BigDecimal averagePrice = null;
        if (marketStatisticsExcel.getAveragePrice() != null) {
            averagePrice = new BigDecimal(marketStatisticsExcel.getAveragePrice().toString()).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        }
        marketStatistics.setAveragePrice(averagePrice);
        BigDecimal sales = null;
        if (marketStatisticsExcel.getSales() != null) {
            sales = new BigDecimal(marketStatisticsExcel.getSales().toString()).setScale(2,BigDecimal.ROUND_HALF_DOWN);
        }
        marketStatistics.setSales(sales);
        list.add(marketStatistics);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        marketStatisticsService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

package com.arg.smart.web.statistics.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.statistics.entity.BuyersIndex;
import com.arg.smart.web.statistics.entity.vo.BuyersIndexExcel;
import com.arg.smart.web.statistics.service.BuyersIndexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BuyersIndexDataListener extends AnalysisEventListener<BuyersIndexExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final BuyersIndexService buyersIndexService;

    public BuyersIndexDataListener(BuyersIndexService buyersIndexService) {
        this.buyersIndexService = buyersIndexService;
    }

    List<BuyersIndex> list = new ArrayList<>();

    @Override
    public void invoke(BuyersIndexExcel buyersIndexExcel, AnalysisContext analysisContext) {
        log.info(buyersIndexExcel.toString());
        BuyersIndex buyersIndex = new BuyersIndex();
        BeanUtils.copyProperties(buyersIndexExcel, buyersIndex);
        //对BigDecimal格式化转换
        BigDecimal temp = null;
        try {
            temp = new BigDecimal(buyersIndexExcel.getTemp().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        } catch (NumberFormatException e) {
            log.error("BigDecimal转换异常");
        }
        buyersIndex.setTemp(temp);
        list.add(buyersIndex);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        buyersIndexService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

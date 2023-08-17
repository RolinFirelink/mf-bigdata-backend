package com.arg.smart.web.customer.utils;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.customer.entity.HotWord;
import com.arg.smart.web.customer.entity.vo.HotWordExcel;
import com.arg.smart.web.customer.service.HotWordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class HotWordDataListener extends AnalysisEventListener<HotWordExcel> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final HotWordService hotWordService;

    public HotWordDataListener(HotWordService hotWordService) {
        this.hotWordService = hotWordService;
    }

    List<HotWord> list = new ArrayList<>();

    //读取数据会执行这方法
    @Override
    public void invoke(HotWordExcel hotWordExcel, AnalysisContext analysisContext) {
        log.info(hotWordExcel.toString());
        HotWord hotWord = new HotWord();
        BeanUtils.copyProperties(hotWordExcel,hotWord);
        list.add(hotWord);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        hotWordService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

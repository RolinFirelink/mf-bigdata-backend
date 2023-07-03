package com.arg.smart.web.cargo.uitls;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.cargo.entity.FreightProgress;
import com.arg.smart.web.cargo.entity.ProductCirculationData;
import com.arg.smart.web.cargo.entity.vo.FreightProgressExcel;
import com.arg.smart.web.cargo.entity.vo.ProductCirculationDataExcel;
import com.arg.smart.web.cargo.service.FreightProgressService;
import com.arg.smart.web.cargo.service.ProductCirculationDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class FreightProgressListener extends AnalysisEventListener<FreightProgressExcel> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final FreightProgressService freightProgressService;

    public FreightProgressListener(FreightProgressService freightProgressService) {
        this.freightProgressService = freightProgressService;
    }

    List<FreightProgress> list = new ArrayList<>();

    //读取数据会执行这方法
    @Override
    public void invoke(FreightProgressExcel freightProgressExcel, AnalysisContext analysisContext) {
        log.info(freightProgressService.toString());
        FreightProgress freightProgress = new FreightProgress();
        String freightNumber = freightProgressExcel.getFreightNumber();
        if(freightNumber != null){
            freightProgress.setFreightNumber(Integer.valueOf(freightNumber));
        }
        BeanUtils.copyProperties(freightProgressExcel,freightProgress);
        list.add(freightProgress);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        freightProgressService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

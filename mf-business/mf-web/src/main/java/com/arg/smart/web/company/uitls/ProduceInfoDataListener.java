package com.arg.smart.web.company.uitls;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.company.entity.ProduceInfo;
import com.arg.smart.web.company.entity.vo.ProduceInfoExcel;
import com.arg.smart.web.company.service.ProduceInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProduceInfoDataListener extends AnalysisEventListener<ProduceInfoExcel> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final ProduceInfoService produceInfoService;

    public ProduceInfoDataListener(ProduceInfoService produceInfoService) {
        this.produceInfoService = produceInfoService;
    }

    List<ProduceInfo> list = new ArrayList<>();

    //读取数据会执行这方法
    @Override
    public void invoke(ProduceInfoExcel produceInfoExcel, AnalysisContext analysisContext) {
        log.info(produceInfoExcel.toString());
        ProduceInfo produceInfo = new ProduceInfo();
        BeanUtils.copyProperties(produceInfoExcel, produceInfo);
        // 对BigDecimal类型的字段进行数据转换
        BigDecimal price = null;
        try {
            price = new BigDecimal(produceInfoExcel.getPrice().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        } catch (NumberFormatException e) {
            log.error("Invalid price value: {}", produceInfoExcel.getPrice());
            // 处理转换失败的情况，例如给字段设置默认值或抛出异常
        }
        produceInfo.setPrice(price);

        BigDecimal projectedProduction = null;
        try {
            projectedProduction = new BigDecimal(produceInfoExcel.getProjectedProduction().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
        } catch (NumberFormatException e) {
            log.error("Invalid projectedProduction value: {}", produceInfoExcel.getProjectedProduction());
            // 处理转换失败的情况，例如给字段设置默认值或抛出异常
        }
        produceInfo.setProjectedProduction(projectedProduction);
        list.add(produceInfo);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        produceInfoService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

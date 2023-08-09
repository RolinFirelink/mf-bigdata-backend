package com.arg.smart.web.statistics.utils;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.statistics.entity.ProvinceSupply;
import com.arg.smart.web.statistics.entity.vo.ProvinceSupplyExcel;
import com.arg.smart.web.statistics.service.ProvinceSupplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProvinceSupplyDataListener extends AnalysisEventListener<ProvinceSupplyExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final ProvinceSupplyService provinceSupplyService;

    public ProvinceSupplyDataListener(ProvinceSupplyService provinceSupplyService) {
        this.provinceSupplyService = provinceSupplyService;
    }

    List<ProvinceSupply> list = new ArrayList<>();
    
    @Override
    public void invoke(ProvinceSupplyExcel provinceSupplyExcel, AnalysisContext analysisContext) {
        log.info(provinceSupplyExcel.toString());
        ProvinceSupply provinceSupply = new ProvinceSupply();
        BeanUtils.copyProperties(provinceSupplyExcel, provinceSupply);
        list.add(provinceSupply);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        provinceSupplyService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

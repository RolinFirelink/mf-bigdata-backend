package com.arg.smart.web.data.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.data.entity.BaseProductProductionScale;
import com.arg.smart.web.data.entity.vo.BaseProductProductionScaleExcel;
import com.arg.smart.web.data.service.BaseProductProductionScaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class BaseProductProductionScaleDataListener extends AnalysisEventListener<BaseProductProductionScaleExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final BaseProductProductionScaleService baseProductProductionScaleService;

    public BaseProductProductionScaleDataListener(BaseProductProductionScaleService baseProductProductionScaleService) {
        this.baseProductProductionScaleService = baseProductProductionScaleService;
    }

    List<BaseProductProductionScale> list = new ArrayList<>();

    @Override
    public void invoke(BaseProductProductionScaleExcel baseProductProductionScaleExcel, AnalysisContext analysisContext) {
        log.info(baseProductProductionScaleExcel.toString());
        BaseProductProductionScale baseProductProductionScale = new BaseProductProductionScale();
        BeanUtils.copyProperties(baseProductProductionScaleExcel, baseProductProductionScale);
        // 对BigDecimal类型的字段进行数据转换
        BigDecimal productionScale = null;
        if (baseProductProductionScaleExcel.getProductionScale() != null) {
            try {
                productionScale = new BigDecimal(baseProductProductionScaleExcel.getProductionScale().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            } catch (NumberFormatException e) {
                log.error("Invalid price value: {}", baseProductProductionScaleExcel.getProductionScale());
                // 处理转换失败的情况，例如给字段设置默认值或抛出异常
            }
        }
        baseProductProductionScale.setProductionScale(productionScale);
        list.add(baseProductProductionScale);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        baseProductProductionScaleService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

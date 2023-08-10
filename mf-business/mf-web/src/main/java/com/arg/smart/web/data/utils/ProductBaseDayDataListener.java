package com.arg.smart.web.data.utils;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.data.entity.ProductBaseDayData;
import com.arg.smart.web.data.entity.vo.ProductBaseDayDataExcel;
import com.arg.smart.web.data.service.ProductBaseDayDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductBaseDayDataListener extends AnalysisEventListener<ProductBaseDayDataExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final ProductBaseDayDataService productBaseDayDataService;

    public ProductBaseDayDataListener(ProductBaseDayDataService productBaseDayDataService) {
        this.productBaseDayDataService = productBaseDayDataService;
    }

    List<ProductBaseDayData> list = new ArrayList<>();

    @Override
    public void invoke(ProductBaseDayDataExcel productBaseDayDataExcel, AnalysisContext analysisContext) {
        log.info(productBaseDayDataExcel.toString());
        ProductBaseDayData productBaseDayData = new ProductBaseDayData();
        BeanUtils.copyProperties(productBaseDayDataExcel, productBaseDayData);
        // 对BigDecimal类型的字段进行数据转换
        BigDecimal supply = null;
        if (productBaseDayDataExcel.getSupply() != null) {
            try {
                supply = new BigDecimal(productBaseDayDataExcel.getSupply().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            } catch (NumberFormatException e) {
                log.error("Invalid price value: {}", productBaseDayDataExcel.getSupply());
                // 处理转换失败的情况，例如给字段设置默认值或抛出异常
            }
        }
        productBaseDayData.setSupply(supply);
        BigDecimal yield = null;
        if (productBaseDayDataExcel.getYield() != null) {
            try {
                yield = new BigDecimal(productBaseDayDataExcel.getYield().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            } catch (NumberFormatException e) {
                log.error("Invalid price value: {}", productBaseDayDataExcel.getYield());
                // 处理转换失败的情况，例如给字段设置默认值或抛出异常
            }
        }
        productBaseDayData.setYield(yield);
        BigDecimal sales = null;
        if (productBaseDayDataExcel.getSales() != null) {
            try {
                sales = new BigDecimal(productBaseDayDataExcel.getSales().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            } catch (NumberFormatException e) {
                log.error("Invalid price value: {}", productBaseDayDataExcel.getSales());
                // 处理转换失败的情况，例如给字段设置默认值或抛出异常
            }
        }
        productBaseDayData.setSales(sales);
        BigDecimal demand = null;
        if (productBaseDayDataExcel.getDemand() != null) {
            try {
                demand = new BigDecimal(productBaseDayDataExcel.getDemand().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            } catch (NumberFormatException e) {
                log.error("Invalid price value: {}", productBaseDayDataExcel.getDemand());
                // 处理转换失败的情况，例如给字段设置默认值或抛出异常
            }
        }
        productBaseDayData.setDemand(demand);
        BigDecimal salesVolume = null;
        if (productBaseDayDataExcel.getSalesVolume() != null) {
            try {
                salesVolume = new BigDecimal(productBaseDayDataExcel.getSalesVolume().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            } catch (NumberFormatException e) {
                log.error("Invalid price value: {}", productBaseDayDataExcel.getSalesVolume());
                // 处理转换失败的情况，例如给字段设置默认值或抛出异常
            }
        }
        productBaseDayData.setSalesVolume(salesVolume);
        list.add(productBaseDayData);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        productBaseDayDataService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

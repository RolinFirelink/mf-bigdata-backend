package com.arg.smart.web.data.utils;


import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.data.entity.PlaceOfSale;
import com.arg.smart.web.data.entity.vo.PlaceOfSaleDataExcel;
import com.arg.smart.web.data.service.PlaceOfSaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class PlaceOfSaleDataListener extends AnalysisEventListener<PlaceOfSaleDataExcel> {
    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final PlaceOfSaleService placeOfSaleService;

    public PlaceOfSaleDataListener(PlaceOfSaleService placeOfSaleService) {
        this.placeOfSaleService = placeOfSaleService;
    }

    List<PlaceOfSale> list = new ArrayList<>();

    @Override
    public void invoke(PlaceOfSaleDataExcel placeOfSaleDataExcel, AnalysisContext analysisContext) {
        log.info(placeOfSaleDataExcel.toString());
        PlaceOfSale placeOfSale = new PlaceOfSale();
        BeanUtils.copyProperties(placeOfSaleDataExcel, placeOfSale);
        // 对BigDecimal类型的字段进行数据转换
        BigDecimal lat = null;
        if (placeOfSaleDataExcel.getLat() != null) {
            try {
                lat = new BigDecimal(placeOfSaleDataExcel.getLat().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            } catch (NumberFormatException e) {
                log.error("Invalid Lat value: {}", placeOfSaleDataExcel.getLat());
                // 处理转换失败的情况，例如给字段设置默认值或抛出异常
            }
        }
        placeOfSale.setLat(lat);
        BigDecimal lng = null;
        if (placeOfSaleDataExcel.getLng() != null) {
            try {
                lng = new BigDecimal(placeOfSaleDataExcel.getLng().toString()).setScale(2, BigDecimal.ROUND_HALF_DOWN);
            } catch (NumberFormatException e) {
                log.error("Invalid price value: {}", placeOfSaleDataExcel.getLng());
                // 处理转换失败的情况，例如给字段设置默认值或抛出异常
            }
        }
        placeOfSale.setLng(lng);
        list.add(placeOfSale);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        placeOfSaleService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

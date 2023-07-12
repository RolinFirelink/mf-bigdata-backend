package com.arg.smart.web.cargo.uitls;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.cargo.entity.ProductCirculationData;
import com.arg.smart.web.cargo.entity.vo.ProductCirculationDataExcel;
import com.arg.smart.web.cargo.service.ProductCirculationDataService;
import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.entity.vo.ProductBaseExcel;
import com.arg.smart.web.company.service.ProductBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class ProductCirculationDataListener extends AnalysisEventListener<ProductCirculationDataExcel> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final ProductCirculationDataService productCirculationDataService;

    public ProductCirculationDataListener(ProductCirculationDataService productCirculationDataService) {
        this.productCirculationDataService = productCirculationDataService;
    }

    List<ProductCirculationData> list = new ArrayList<>();

    //读取数据会执行这方法
    @Override
    public void invoke(ProductCirculationDataExcel productCirculationDataExcel, AnalysisContext analysisContext) {
        log.info(productCirculationDataExcel.toString());
        ProductCirculationData productCirculationData = new ProductCirculationData();
        String id = productCirculationDataExcel.getId();
        if(id != null){
            productCirculationData.setId(Long.valueOf(id));
        }
        String dateManufacture = productCirculationDataExcel.getDateManufacture();
        if(dateManufacture != null){
            productCirculationData.setDateManufacture(new Date(dateManufacture));
        }
        String deliveryTime = productCirculationDataExcel.getDeliveryTime();
        if(deliveryTime != null){
            productCirculationData.setDeliveryTime(new Date(deliveryTime));
        }
        String receivingTime = productCirculationDataExcel.getReceivingTime();
        if(receivingTime != null){
            productCirculationData.setReceivingTime(new Date(receivingTime));
        }
        String transportationTime = productCirculationDataExcel.getTransportationTime();
        if(transportationTime != null){
            productCirculationData.setTransportationTime(Integer.valueOf(transportationTime));
        }
        String transportationQuantity = productCirculationDataExcel.getTransportationQuantity();
        if(transportationQuantity != null){
            productCirculationData.setTransportationQuantity(Long.valueOf(transportationQuantity));
        }
        String companyId = productCirculationDataExcel.getCompanyId();
        if(companyId != null){
            productCirculationData.setCompanyId(Long.valueOf(companyId));
        }
        String orderId = productCirculationDataExcel.getOrderId();
        if(orderId != null){
            productCirculationData.setOrderId(Long.valueOf(orderId));
        }
        String businessType = productCirculationDataExcel.getBusinessType();
//        if(businessType != null){
//            productCirculationData.setBusinessType(String.valueOf(businessType));
//        }
        BeanUtils.copyProperties(productCirculationDataExcel,productCirculationData);
        list.add(productCirculationData);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        productCirculationDataService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

package com.arg.smart.web.company.uitls;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.entity.vo.ProductBaseExcel;
import com.arg.smart.web.company.service.ProductBaseService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProductBaseDataListener extends AnalysisEventListener<ProductBaseExcel> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final ProductBaseService productBaseService;

    public ProductBaseDataListener(ProductBaseService productBaseService) {
        this.productBaseService = productBaseService;
    }

    List<ProductBase> list = new ArrayList<>();

    //读取数据会执行这方法
    @Override
    public void invoke(ProductBaseExcel productBaseExcel, AnalysisContext analysisContext) {
        log.info(productBaseExcel.toString());
        ProductBase productBase = new ProductBase();
        String id = productBaseExcel.getId();
        if(id != null){
            productBase.setId(Long.valueOf(id));
        }
        productBase.setCompanyName(productBaseExcel.getCompanyName());
        productBase.setCompanyNo(productBaseExcel.getCompanyNo());
        productBase.setContacts(productBaseExcel.getContacts());
        productBase.setContactPhone(productBaseExcel.getContactPhone());
        productBase.setEmail(productBaseExcel.getEmail());
        productBase.setAreaName(productBaseExcel.getAreaName());
        productBase.setAreaCode(productBaseExcel.getAreaCode());
        productBase.setAddress(productBaseExcel.getAddress());
        productBase.setProductType(productBaseExcel.getProductType());
        String enabled = productBaseExcel.getEnabled();
        if(enabled!= null){
            productBase.setEnabled(Integer.valueOf(enabled));
        }
        String parentId = productBaseExcel.getParentId();
        if(parentId != null){
            productBase.setParentId(Long.valueOf(parentId));
        }
        productBase.setRemark(productBaseExcel.getRemark());
        list.add(productBase);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        productBaseService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

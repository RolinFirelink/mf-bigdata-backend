package com.arg.smart.web.customer.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.customer.entity.CustomerBehavior;
import com.arg.smart.web.customer.entity.vo.CustomerBehaviorExcel;
import com.arg.smart.web.customer.service.CustomerBehaviorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class CustomerBehaviorListener extends AnalysisEventListener<CustomerBehaviorExcel> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final CustomerBehaviorService customerBehaviorService;

    public CustomerBehaviorListener(CustomerBehaviorService customerBehaviorService) {
        this.customerBehaviorService = customerBehaviorService;
    }

    List<CustomerBehavior> list = new ArrayList<>();

    //读取数据会执行这方法
    @Override
    public void invoke(CustomerBehaviorExcel customerBehaviorService, AnalysisContext analysisContext) {
        log.info(customerBehaviorService.toString());
        CustomerBehavior customerBehavior = new CustomerBehavior();
        BeanUtils.copyProperties(customerBehaviorService,customerBehavior);
        list.add(customerBehavior);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        customerBehaviorService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

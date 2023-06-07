package com.arg.smart.web.customer.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.customer.entity.Customer;
import com.arg.smart.web.customer.entity.vo.CustomerExcel;
import com.arg.smart.web.customer.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CustomerListener extends AnalysisEventListener<CustomerExcel> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final CustomerService customerService;

    public CustomerListener(CustomerService customerService) {
        this.customerService = customerService;
    }

    List<Customer> list = new ArrayList<>();

    //读取数据会执行这方法
    @Override
    public void invoke(CustomerExcel customerExcel, AnalysisContext analysisContext) {
        log.info(customerExcel.toString());
        Customer customer = new Customer();
        BeanUtils.copyProperties(customerExcel,customer);
        list.add(customer);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        customerService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

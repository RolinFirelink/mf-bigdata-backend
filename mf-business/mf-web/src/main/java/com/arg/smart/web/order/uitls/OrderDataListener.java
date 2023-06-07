package com.arg.smart.web.order.uitls;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.entity.vo.OrderExcel;
import com.arg.smart.web.order.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
public class OrderDataListener extends AnalysisEventListener<OrderExcel> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final OrderService orderService;

    public OrderDataListener(OrderService orderService) {
        this.orderService = orderService;
    }

    List<Order> list = new ArrayList<>();

    //读取数据会执行这方法
    @Override
    public void invoke(OrderExcel orderExcel, AnalysisContext analysisContext) {
        log.info(orderExcel.toString());
        Order order = new Order();
        String id = orderExcel.getId();
        if (id != null) {
            order.setId(Long.valueOf(id));
        }
        String category = orderExcel.getCategory();
        if(category != null){
            order.setCategory(Integer.valueOf(category));
        }
        String vendorId = orderExcel.getVendorId();
        if (vendorId != null) {
            order.setVendorId(Long.valueOf(vendorId));
        }
        String buyerId = orderExcel.getBuyerId();
        if(buyerId != null){
            order.setBuyerId(Long.valueOf(buyerId));
        }
        String status = orderExcel.getStatus();
        if (status != null) {
            order.setStatus(Integer.valueOf(status));
        }
        String startTime = orderExcel.getStartTime();
        if(startTime != null){
            order.setStartTime(new Date(startTime));
        }
        String finishTime = orderExcel.getFinishTime();
        if(finishTime != null){
            order.setFinishTime(new Date(finishTime));
        }
        BeanUtils.copyProperties(orderExcel,order);
        list.add(order);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        orderService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

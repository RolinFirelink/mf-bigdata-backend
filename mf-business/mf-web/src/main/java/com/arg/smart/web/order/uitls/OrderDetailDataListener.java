package com.arg.smart.web.order.uitls;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.order.entity.OrderDetail;
import com.arg.smart.web.order.entity.vo.OrderDetailExcel;
import com.arg.smart.web.order.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class OrderDetailDataListener extends AnalysisEventListener<OrderDetailExcel> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final OrderDetailService orderDetailService;

    public OrderDetailDataListener(OrderDetailService orderDetailService) {
        this.orderDetailService = orderDetailService;
    }

    List<OrderDetail> list = new ArrayList<>();

    //读取数据会执行这方法
    @Override
    public void invoke(OrderDetailExcel orderDetailExcel, AnalysisContext analysisContext) {
        log.info(orderDetailExcel.toString());
        OrderDetail orderDetail = new OrderDetail();
        BeanUtils.copyProperties(orderDetailExcel,orderDetail);
        list.add(orderDetail);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        orderDetailService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

package com.arg.smart.web.order.service;


import com.arg.smart.web.order.entity.OrderDetail;
import com.arg.smart.web.order.req.ReqOrderDetail;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @description: 订单数据明细表
 * @author cgli
 * @date: 2023-05-22
 * @version: V1.0.0
 */
public interface OrderDetailService extends IService<OrderDetail> {

    /**
     * 条件查询订单明细表
     * @param parentId 订单明细表查询条件
     * @return List<OrderDetail>
     */
    List<OrderDetail> list(Long parentId);

    List<String> averageSales(Integer time, Integer timeFlag);

    List<String> averageSales(String startTime, String endTime, Integer flag);
}

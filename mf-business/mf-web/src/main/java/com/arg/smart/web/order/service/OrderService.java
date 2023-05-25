package com.arg.smart.web.order.service;

import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.req.ReqOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 订单数据主表
 * @author cgli
 * @date: 2023-05-19
 * @version: V1.0.0
 */
public interface OrderService extends IService<Order> {
    /**
     * 条件查询订单主表
     * @param reqOrder 订单主表查询条件
     * @return List<Order>
     */
    List<Order> list(ReqOrder reqOrder);
}

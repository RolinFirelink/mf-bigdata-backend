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

    List<Order> list(ReqOrder reqOrder);
}

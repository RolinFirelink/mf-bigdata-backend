package com.arg.smart.web.order.service.impl;

import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.mapper.OrderMapper;
import com.arg.smart.web.order.service.OrderService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @description: 订单数据主表
 * @author cgli
 * @date: 2023-05-19
 * @version: V1.0.0
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}

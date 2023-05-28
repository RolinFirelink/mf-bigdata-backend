package com.arg.smart.web.order.service.impl;

import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.entity.OrderDetail;
import com.arg.smart.web.order.mapper.OrderDetailMapper;
import com.arg.smart.web.order.mapper.OrderMapper;
import com.arg.smart.web.order.req.ReqOrder;
import com.arg.smart.web.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 订单数据主表
 * @date: 2023-05-19
 * @version: V1.0.0
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderDetailMapper orderDetailMapper;

    @Override
    public List<Order> list(ReqOrder reqOrder) {
        return this.list().stream().peek(item->{
            List<OrderDetail> orderDetailList = orderDetailMapper.listByOrderId(item.getId());
            item.setOrderDetailList(orderDetailList);
        }).collect(Collectors.toList());
    }
}

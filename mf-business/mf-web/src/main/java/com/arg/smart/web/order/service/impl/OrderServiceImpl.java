package com.arg.smart.web.order.service.impl;

import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.mapper.OrderMapper;
import com.arg.smart.web.order.req.ReqOrder;
import com.arg.smart.web.order.service.OrderDetailService;
import com.arg.smart.web.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 订单数据主表
 * @author cgli
 * @date: 2023-05-19
 * @version: V1.0.0
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderDetailService orderDetailService;

    @Override
    public List<Order> list(ReqOrder reqOrder) {
        if(reqOrder == null){
            return this.list();
        }
        QueryWrapper<Order> orderQueryWrapper = new QueryWrapper<>();
        Integer category = reqOrder.getCategory();
        if(category != null){
            orderQueryWrapper.eq("category",category);
        }
        return this.list(orderQueryWrapper).stream().peek(item->{
            item.setOrderDetailList(orderDetailService.list(item.getId()));
        }).collect(Collectors.toList());
    }
}

package com.arg.smart.web.order.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.entity.OrderDetail;
import com.arg.smart.web.order.mapper.OrderDetailMapper;
import com.arg.smart.web.order.mapper.OrderMapper;
import com.arg.smart.web.order.req.ReqOrder;
import com.arg.smart.web.order.service.OrderDetailService;
import com.arg.smart.web.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.Date;
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
    private OrderDetailService orderDetailService;

    @Override
    public PageResult<Order> list(ReqOrder reqOrder) {
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        //需条件查询的参数
        Integer category = reqOrder.getCategory();
        String vendorName = reqOrder.getVendorName();
        String buyerName = reqOrder.getBuyerName();
        Integer status = reqOrder.getStatus();
        Date createStartTime = reqOrder.getCreateStartTime();
        Date createEndTime = reqOrder.getCreateEndTime();
        Date finishStartTime = reqOrder.getFinishStartTime();
        Date finishEndTime = reqOrder.getFinishEndTime();
        Integer flag = reqOrder.getFlag();
        if (category != null) {
            queryWrapper.eq(Order::getCategory, category);
        }
        if(vendorName != null){
            queryWrapper.like(Order::getVendorName,vendorName);
        }
        if(buyerName != null){
            queryWrapper.like(Order::getBuyerName,buyerName);
        }
        if(status != null){
            queryWrapper.eq(Order::getStatus,status);
        }
        if(createStartTime != null && createEndTime != null){
            queryWrapper.ge(Order::getStartTime,createStartTime).le(Order::getStartTime,createEndTime);
        }
        if(finishStartTime != null && finishEndTime != null){
            queryWrapper.ge(Order::getFinishTime,finishStartTime).le(Order::getFinishTime,finishEndTime);
        }
        if(flag != null){
            queryWrapper.eq(Order::getFlag,flag);
        }
        List<Order> list = this.list(queryWrapper);
        PageResult<Order> pageResult = new PageResult<>(list);
        List<Order> collect = list.stream().peek(item -> item.setOrderDetailList(orderDetailService.list(item.getId()))).collect(Collectors.toList());
        pageResult.setList(collect);
        return pageResult;
    }
}

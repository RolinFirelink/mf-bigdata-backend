package com.arg.smart.web.average.service.impl;

import com.arg.smart.web.average.entity.AveragePrice;
import com.arg.smart.web.average.mapper.AveragePriceMapper;
import com.arg.smart.web.average.service.AveragePriceService;
import com.arg.smart.web.cargo.service.ProductCirculationDataService;
import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.entity.OrderDetail;
import com.arg.smart.web.order.service.OrderDetailService;
import com.arg.smart.web.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 均价表
 * @author cgli
 * @date: 2023-06-01
 * @version: V1.0.0
 */
@Service
public class AveragePriceServiceImpl extends ServiceImpl<AveragePriceMapper, AveragePrice> implements AveragePriceService {

    @Resource
    private RedisTemplate<String,AveragePrice> redisTemplate;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private ProductCirculationDataService productCirculationDataService;

    @Override
    public boolean timingSave() {
        LambdaQueryWrapper<Order> orderLambdaQueryWrapper = new LambdaQueryWrapper<>();
        orderLambdaQueryWrapper.eq(Order::getCategory,4);
        List<Order> orderList = orderService.list(orderLambdaQueryWrapper);
        LambdaQueryWrapper<OrderDetail> detailLambdaQueryWrapper = new LambdaQueryWrapper<>();
        List<Long> orderIdList = orderList.stream().map(Order::getId).collect(Collectors.toList());
        detailLambdaQueryWrapper.in(OrderDetail::getId,orderIdList);
        List<OrderDetail> detailList = orderDetailService.list(detailLambdaQueryWrapper);
        
        return false;
    }
}

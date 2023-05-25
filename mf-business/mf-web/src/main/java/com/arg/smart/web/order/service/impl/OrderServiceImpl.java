package com.arg.smart.web.order.service.impl;

import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.mapper.OrderMapper;
import com.arg.smart.web.order.req.ReqOrder;
import com.arg.smart.web.order.service.OrderService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cgli
 * @description: 订单数据主表
 * @date: 2023-05-19
 * @version: V1.0.0
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Resource
    private OrderMapper orderDao;

    @Override
    public List<Order> list(ReqOrder reqOrder) {
        if (reqOrder == null) {
            return orderDao.selectList(null);
        }
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(reqOrder.getCompanyId() != null, Order::getCompanyId, reqOrder.getCompanyId());
        wrapper.eq(reqOrder.getCompanyNo() != null && !" ".equals(reqOrder.getCompanyNo())
                , Order::getCompanyNo, reqOrder.getCompanyNo());
        wrapper.like(reqOrder.getCompanyName() != null && !" ".equals(reqOrder.getCompanyName())
                , Order::getCompanyName, reqOrder.getCompanyName());
        return orderDao.selectList(wrapper);
    }
}

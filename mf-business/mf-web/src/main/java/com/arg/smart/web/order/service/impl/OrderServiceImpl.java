package com.arg.smart.web.order.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.mapper.OrderMapper;
import com.arg.smart.web.order.req.ReqOrder;
import com.arg.smart.web.order.service.OrderDetailService;
import com.arg.smart.web.order.service.OrderService;
import com.arg.smart.web.order.vo.DurationQueryParam;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 订单数据主表
 * @date: 2023-05-19
 * @version: V1.0.0
 */
@Service
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    // 订单主表 Mapper
    @Resource
    private OrderMapper orderMapper;
    // 订单子表服务
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

    @Override
    public Long getOrderCountByTime(Integer flag, Integer category, DurationQueryParam durationQueryParam) {
        LambdaQueryWrapper<Order> wrapper = this.getOrderLambdaQueryWrapper(flag, category, durationQueryParam);
        wrapper.eq(flag != null, Order::getFlag, flag);
        wrapper.eq(category != null, Order::getCategory, category);
        return this.count(wrapper);
    }

    @Override
    public Map<String, Object> getOrderCountByTransportMode(Integer flag, Integer category, DurationQueryParam durationQueryParam) {
        List<Long> orderIds = this.getOrderIds(flag, category, durationQueryParam);
        if (orderIds.isEmpty()) {
            return Collections.emptyMap();
        }
        return orderMapper.selectOrderCountByTransportMode(orderIds);
    }

    @Override
    public Map<String, Object> getOrderTransportationAmount(Integer flag, Integer category, DurationQueryParam durationQueryParam) {
        List<Long> orderIds = this.getOrderIds(flag, category, durationQueryParam);
        if (orderIds.isEmpty()) {
            return Collections.emptyMap();
        }
        return orderMapper.getOrderTransportationAmount(orderIds);
    }

    @Override
    public Map<String, Object> getOrderAmountByArea(Integer flag, Integer category, DurationQueryParam durationQueryParam) {
        List<Long> orderIds = this.getOrderIds(flag, category, durationQueryParam);
        if (orderIds.isEmpty()) {
            return Collections.emptyMap();
        }
        return orderMapper.getOrderAmountByArea(orderIds);
    }

    @Override
    public Map<String, Object> getProductAvgPriceByArea(DurationQueryParam durationQueryParam, Integer category, Long goodId) {
        return orderMapper.getProductAvgPriceByArea(category, goodId, durationQueryParam.getStartTime(), durationQueryParam.getEndTime());
    }

    /**
     * 获取订单主表条件查询对象
     *
     * @param flag     模块编号
     * @param category 订单类型
     * @param param    时间段查询值对象
     * @return LambdaQueryWrapper<Order>
     */
    private LambdaQueryWrapper<Order> getOrderLambdaQueryWrapper(Integer flag, Integer category, DurationQueryParam param) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.ge(param.getStartTime() != null, Order::getFinishTime, param.getStartTime())
                .le(param.getEndTime() != null, Order::getFinishTime, param.getEndTime())
                .eq(flag != null, Order::getFlag, flag)
                .eq(category != null, Order::getCategory, category);
        return wrapper;
    }

    /**
     * 获取符合订单主表条件查询对象条件的订单主表 ID
     *
     * @param flag     模块编号
     * @param category 订单类型
     * @param param    时间段查询值对象
     * @return List<Long>
     */
    private List<Long> getOrderIds(Integer flag, Integer category, DurationQueryParam param) {
        return this.list(this.getOrderLambdaQueryWrapper(flag, category, param)).stream().map(Order::getId).collect(Collectors.toList());
    }
}

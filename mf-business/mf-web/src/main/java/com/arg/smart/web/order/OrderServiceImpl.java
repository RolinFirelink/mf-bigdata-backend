package com.arg.smart.web.order.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.entity.OrderDetail;
import com.arg.smart.web.order.mapper.OrderMapper;
import com.arg.smart.web.order.req.ReqOrder;
import com.arg.smart.web.order.service.OrderDetailService;
import com.arg.smart.web.order.service.OrderService;
import com.arg.smart.web.order.vo.DurationQueryParam;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
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
        Integer category = reqOrder.getCategory();
        if (category != null) {
            queryWrapper.eq(Order::getCategory, category);
        }
        List<Order> list = this.list(queryWrapper);
        PageResult<Order> pageResult = new PageResult<>(list);
        List<Order> collect = list.stream().peek(item -> {
            item.setOrderDetailList(orderDetailService.list(item.getId()));
        }).collect(Collectors.toList());
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

    @Override
    public Map<String, Object> getProductAvgPriceByArea(DurationQueryParam durationQueryParam, Integer category, Long goodId) {
        return orderMapper.getProductAvgPriceByArea(category, goodId, durationQueryParam.getStartTime(), durationQueryParam.getEndTime());
    }

    @Override
    public List<Integer> getMarketEstimatesByFlagAndMaterialId(Integer flag, Long materialId) {
        return orderMapper.getMarketEstimatesByFlagAndMaterialId(flag,materialId);
    }

    @Override
    public List<BigDecimal> getBatchProductionByFlagAndMaterialId(Integer flag, Long materialId, Integer batch) {
        return orderMapper.getBatchProductionByFlagAndMaterialId(flag,materialId,batch);
    }

    @Override
    public Long getOrderCountByFlagAndTimeAndCategory(Integer flag, DurationQueryParam durationQueryParam, Integer category) {

        LambdaQueryWrapper<Order> wrapper = this.getOrderLambdaQueryWrapper(flag, category, durationQueryParam);
        wrapper.eq(flag != null, Order::getFlag, flag);
        wrapper.eq(category != null, Order::getCategory, category);
        return this.count(wrapper);
        //return orderMapper.getOrderCountByFlagAndTimeAndCategory(flag,durationQueryParam.getStartTime(), durationQueryParam.getEndTime(),category);
    }

    @Override
    public List<MaterialProduce> getOrderDetailsByFlagAndTimeAndMaterialId(Integer flag, DurationQueryParam durationQueryParam, Long materialId) {
        return orderMapper.getOrderDetailsByFlagAndTimeAndMaterialId(flag,durationQueryParam.getStartTime(), durationQueryParam.getEndTime(),materialId);
    }

    @Override
    public List<BigDecimal> getProductionTotalByFlagAndTimeAndMaterialId(Integer flag, DurationQueryParam durationQueryParam, Long materialId) {
        return orderMapper.getProductionTotalByFlagAndTimeAndMaterialId(flag,durationQueryParam.getStartTime(), durationQueryParam.getEndTime(),materialId);
    }

    @Override
    public List<Long> getInventoryQuantityByFlagAndTimeAndMaterialId(Integer flag, DurationQueryParam durationQueryParam, Long materialId) {
        return orderMapper.getInventoryQuantityByFlagAndTimeAndMaterialId(flag,durationQueryParam.getStartTime(), durationQueryParam.getEndTime(),materialId);
    }

    @Override
    public List<OrderDetail> getMonthlyOrderDetailsByFlagAndTimeAndMaterialId(Integer flag, DurationQueryParam durationQueryParam, Long materialId) {
        return orderMapper.getMonthlyOrderDetailsByFlagAndTimeAndMaterialId(flag,durationQueryParam.getStartTime(), durationQueryParam.getEndTime(),materialId);
    }
}

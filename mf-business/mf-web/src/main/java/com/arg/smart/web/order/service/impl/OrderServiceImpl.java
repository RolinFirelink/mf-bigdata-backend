package com.arg.smart.web.order.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.entity.OrderDetail;
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
import java.util.Collections;
import java.util.HashMap;
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
        List<Order> collect = list.stream()
                .peek(item -> item.setOrderDetailList(orderDetailService.list(item.getId())))
                .collect(Collectors.toList());
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

    @Override
    public Map<String, Map<String, Object>> getCompanyCirculationInfo(Integer flag, Integer category, DurationQueryParam param) {
        Map<String, Object> cirInfo = orderMapper.getCompanyCirculationInfo(flag, category, param.getStartTime(), param.getEndTime());
        Map<String, Object> transportInfo = orderMapper.getCompanyTransportInfo(param.getStartTime(), param.getEndTime());
        if (cirInfo.isEmpty()) {
            return Collections.emptyMap();
        }

        // 数据有效
        HashMap<String, Map<String, Object>> resultMap = new HashMap<>();
        for (Object v : cirInfo.values()) {
            Map<String, Object> map = (Map<String, Object>) v;
            if (!resultMap.containsKey(map.get("company_name"))) {
                resultMap.put((String) map.get("company_name"), new HashMap<>());
            }
            Map<String, Object> transport = resultMap.get(map.get("company_name"));
            if (!transport.containsKey(map.get("mode_transport"))) {
                transport.put((String) map.get("mode_transport"), 1L);
            } else {
                transport.put((String) map.get("mode_transport"), (Long) transport.get(map.get("mode_transport")) + 1L);
            }
        }
        // 合并承运商运货量，运单数量与运送均价
        for (Object v : transportInfo.values()) {
            Map<String, Object> map = (Map<String, Object>) v;
            if (resultMap.containsKey(map.get("company_name"))) {
                Map<String, Object> transport = resultMap.get(map.get("company_name"));
                transport.put("transport_count", map.get("count"));
                transport.put("transport_amount", map.get("sum"));
                transport.put("unit", map.get("unit"));
                transport.put("avg_price", map.get("avg_price"));
                resultMap.put((String) map.get("company_name"), transport);
            }
        }
        return resultMap;
    }

    @Override
    public List<Order> getOrderByCategory(Integer flag, Integer category, DurationQueryParam param) {
        List<Long> orderIds = this.getOrderIds(flag, category, param);
        if (orderIds.isEmpty()) {
            return Collections.emptyList();
        }

        List<Order> orders = this.list(new LambdaQueryWrapper<Order>().in(Order::getId, orderIds));
        orders.stream().forEach(order -> order
                .setOrderDetailList(orderDetailService
                        .list(new LambdaQueryWrapper<OrderDetail>()
                                .eq(OrderDetail::getOrderId, order.getId()))));
        return orders;
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

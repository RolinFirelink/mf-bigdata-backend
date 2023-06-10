package com.arg.smart.web.order.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.req.ReqOrder;
import com.arg.smart.web.order.vo.DurationQueryParam;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @author cgli
 * @description: 订单数据主表
 * @date: 2023-05-19
 * @version: V1.0.0
 */
public interface OrderService extends IService<Order> {
    /**
     * 条件查询订单主表
     *
     * @param reqOrder 订单主表查询条件
     * @return PageResult<Order>
     */
    PageResult<Order> list(ReqOrder reqOrder);

    /**
     * 根据模块类型和需要统计的时间段统计时间段内订单产生的数量
     *
     * @param flag               模块类型
     * @param durationQueryParam 时间段查询值对象
     * @param category           订单类型
     * @return Long
     */
    Long getOrderCountByTime(Integer flag, Integer category, DurationQueryParam durationQueryParam);

    /**
     * 统计不同运输方式的订单数量
     *
     * @param flag               模块类型
     * @param durationQueryParam 时间段查询值对象
     * @param category           订单类型
     * @return Map<String, Object>
     */
    Map<String, Object> getOrderCountByTransportMode(Integer flag, Integer category, DurationQueryParam durationQueryParam);

    /**
     * 统计不同承运商运量
     *
     * @param flag               模块类型
     * @param category           订单类型
     * @param durationQueryParam 时间段查询值对象
     * @return Map<String, Object>
     */
    Map<String, Object> getOrderTransportationAmount(Integer flag, Integer category, DurationQueryParam durationQueryParam);

    /**
     * 统计不同地域的订单收货者的下单数量
     *
     * @param flag               模块类型
     * @param category           订单类型
     * @param durationQueryParam 时间段查询值对象
     * @return Map<String, Object>
     */
    Map<String, Object> getOrderAmountByArea(Integer flag, Integer category, DurationQueryParam durationQueryParam);

    /**
     * 统计不同地域某产品平均销售价格
     *
     * @param durationQueryParam 时间段查询值对象
     * @param category           订单类型
     * @param goodId             需要查询的产品 ID
     * @return Map<String, Object>
     */
    Map<String, Object> getProductAvgPriceByArea(DurationQueryParam durationQueryParam, Integer category, Long goodId);

}

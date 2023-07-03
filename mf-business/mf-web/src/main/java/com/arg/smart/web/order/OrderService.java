package com.arg.smart.web.order.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.entity.OrderDetail;
import com.arg.smart.web.order.req.ReqOrder;
import com.arg.smart.web.order.vo.DurationQueryParam;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.baomidou.mybatisplus.extension.service.IService;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
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

    /**
     * 统计不同品种产品预计上市产量
     *
     * @param flag          区分字段
     * @param materialId    产品id
     * @return List<Integer>
     */
    List<Integer> getMarketEstimatesByFlagAndMaterialId(Integer flag, Long materialId);

    /**
     * 统计不同品种产品不同批次产量
     *
     * @param flag          区分字段
     * @param materialId    产品id
     * @param batch         生产批次
     * @return List<BigDecimal>
     */
    List<BigDecimal> getBatchProductionByFlagAndMaterialId(Integer flag, Long materialId, Integer batch);

    /**
     * 统计不同品种产品不同批次产量
     *
     * @param flag                    区分字段
     * @param durationQueryParam      时间段查询值对象
     * @param category                订单类型
     * @return Long
     */
    Long getOrderCountByFlagAndTimeAndCategory(Integer flag, DurationQueryParam durationQueryParam, Integer category);

    /**
     * 查询特定产品月生产订单详细信息
     *
     * @param flag                    区分字段
     * @param durationQueryParam      时间段查询值对象
     * @param materialId              产品id
     * @return List<MaterialProduce>
     */
    List<MaterialProduce> getOrderDetailsByFlagAndTimeAndMaterialId(Integer flag, DurationQueryParam durationQueryParam, Long materialId);

    /**
     * 统计不同产品生产总额
     *
     * @param flag                    区分字段
     * @param durationQueryParam      时间段查询值对象
     * @param materialId              产品id
     * @return List<BigDecimal>
     */
    List<BigDecimal> getProductionTotalByFlagAndTimeAndMaterialId(Integer flag, DurationQueryParam durationQueryParam, Long materialId);

    /**
     * 统计不同产品月出库量
     *
     * @param flag                    区分字段
     * @param durationQueryParam      时间段查询值对象
     * @param materialId              产品id
     * @return List<Long>
     */
    List<Long> getInventoryQuantityByFlagAndTimeAndMaterialId(Integer flag, DurationQueryParam durationQueryParam, Long materialId);

    /**
     * 查询月出库订单明细
     *
     * @param flag                     区分字段
     * @param durationQueryParam      时间段查询值对象
     * @param materialId              产品id
     * @return List<OrderDetail>
     */
    List<OrderDetail> getMonthlyOrderDetailsByFlagAndTimeAndMaterialId(Integer flag, DurationQueryParam durationQueryParam, Long materialId);

}

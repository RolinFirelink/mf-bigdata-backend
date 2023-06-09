package com.arg.smart.web.order.mapper;

import com.arg.smart.web.order.entity.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author cgli
 * @description: 订单数据主表
 * @date: 2023-05-19
 * @version: V1.0.0
 */
public interface OrderMapper extends BaseMapper<Order> {

    /**
     * 统计不同运输方式的订单数量
     *
     * @param idList 符合要求的订单主表 ID
     * @return Map<String, Object>
     */
    @MapKey("row_no")
    Map<String, Object> selectOrderCountByTransportMode(List<Long> idList);

    /**
     * 统计不同地域的订单收货者的下单数量
     *
     * @param idList 符合要求的订单主表 ID
     * @return Map<String, Object>
     */
    @MapKey("row_no")
    Map<String, Object> getOrderAmountByArea(List<Long> idList);

    /**
     * 统计不同承运商运量
     *
     * @param idList 符合要求的订单主表 ID
     * @return Map<String, Object>
     */
    @MapKey("row_no")
    Map<String, Object> getOrderTransportationAmount(List<Long> idList);

    /**
     * 统计不同地域某产品平均销售价格
     *
     * @param category  订单类型
     * @param goodId    产品编号
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return Map<String, Object>
     */
    @MapKey("material_id")
    Map<String, Object> getProductAvgPriceByArea(@Param("category") Integer category
            , @Param("material_id") Long goodId, @Param("start_time") Date startTime, @Param("end_time") Date endTime);

}

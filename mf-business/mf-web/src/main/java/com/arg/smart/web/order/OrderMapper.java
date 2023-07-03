package com.arg.smart.web.order.mapper;

import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.entity.OrderDetail;
import com.arg.smart.web.order.vo.DurationQueryParam;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
    @MapKey("row_no")
    Map<String, Object> getProductAvgPriceByArea(@Param("category") Integer category
            , @Param("material_id") Long goodId, @Param("start_time") Date startTime, @Param("end_time") Date endTime);

    /**
     * 统计不同品种产品预计上市产量
     *
     * @param flag          区分字段
     * @param materialId    产品id
     * @return List<Integer>
     */
    List<Integer> getMarketEstimatesByFlagAndMaterialId(@Param("flag") Integer flag, @Param("materialId") Long materialId);

    /**
     * 统计不同品种产品不同批次产量
     *
     * @param flag          区分字段
     * @param materialId    产品id
     * @param batch         生产批次
     * @return List<BigDecimal>
     */
    List<BigDecimal> getBatchProductionByFlagAndMaterialId(@Param("flag") Integer flag, @Param("materialId") Long materialId,@Param("batch") Integer batch);

    /**
     * 统计不同品种产品不同批次产量
     *
     * @param flag           区分字段
     * @param startTime      订单创建时间
     * @param endTime        订单完成时间
     * @param category       订单类型
     * @return Long
     */
    Long getOrderCountByFlagAndTimeAndCategory(@Param("flag") Integer flag, @Param("start_time") Date startTime
                                               , @Param("end_time") Date endTime, @Param("category") Integer category);

   /**
    * 查询特定产品月生产订单详细信息
    *
    * @param flag           区分字段
    * @param startTime      订单创建时间
    * @param endTime        订单完成时间
    * @param materialId    产品id
    * @return List<MaterialProduce>
    */
    List<MaterialProduce> getOrderDetailsByFlagAndTimeAndMaterialId(@Param("flag") Integer flag, @Param("start_time") Date startTime
            , @Param("end_time") Date endTime, @Param("materialId") Long materialId);

    /**
     * 统计不同产品生产总额
     *
     * @param flag           区分字段
     * @param startTime      订单创建时间
     * @param endTime        订单完成时间
     * @param materialId    产品id
     * @return List<BigDecimal>
     */
    List<BigDecimal> getProductionTotalByFlagAndTimeAndMaterialId(@Param("flag") Integer flag, @Param("start_time") Date startTime
            , @Param("end_time") Date endTime, @Param("materialId") Long materialId);

    /**
     * 统计不同产品月出库量
     *
     * @param flag           区分字段
     * @param startTime      订单创建时间
     * @param endTime        订单完成时间
     * @param materialId    产品id
     * @return List<Long>
     */
    List<Long> getInventoryQuantityByFlagAndTimeAndMaterialId(@Param("flag") Integer flag, @Param("start_time") Date startTime
            , @Param("end_time") Date endTime, @Param("materialId") Long materialId);

    /**
     * 查询月出库订单明细
     *
     * @param flag           区分字段
     * @param startTime      订单创建时间
     * @param endTime        订单完成时间
     * @param materialId    产品id
     * @return List<OrderDetail>
     */
    List<OrderDetail> getMonthlyOrderDetailsByFlagAndTimeAndMaterialId(@Param("flag") Integer flag, @Param("start_time") Date startTime
            , @Param("end_time") Date endTime, @Param("materialId") Long materialId);

}

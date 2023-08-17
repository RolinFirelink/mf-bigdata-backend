package com.arg.smart.web.order.mapper;


import com.arg.smart.web.order.entity.OrderDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @description: 订单数据明细表
 * @author cgli
 * @date: 2023-05-22
 * @version: V1.0.0
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {

    @Select("select * from sh_order_detail where parent_id = #{orderId} and delete_flag = 0")
    List<OrderDetail> listByOrderId(@Param("orderId") Long orderId);

    @Select("select SUM(sales_quantity) from sh_order_detail where substring(create_time,1,7) = #{time} AND flag = #{flag} AND material_id = #{materialId}")
    Long totalSalesByMaterialId(@Param("time") String time, @Param("flag") Integer flag, @Param("materialId") Long materialId);

    @Select("select SUM(sales_quantity) from sh_order_detail where substring(create_time,1,7) = #{time} AND flag = #{flag}")
    Long totalSales(@Param("time")String time, @Param("flag")Integer flag);
}

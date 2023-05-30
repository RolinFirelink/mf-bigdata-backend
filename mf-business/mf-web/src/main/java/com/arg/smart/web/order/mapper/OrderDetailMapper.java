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
}

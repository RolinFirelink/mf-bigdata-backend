package com.arg.smart.web.order.entity.vo;

import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.entity.OrderDetail;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderVo {
    private Order order;
    private OrderDetail orderDetail;
}

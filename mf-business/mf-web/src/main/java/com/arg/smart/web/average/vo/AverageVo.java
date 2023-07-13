package com.arg.smart.web.average.vo;

import com.arg.smart.web.cargo.entity.ProductCirculationData;
import com.arg.smart.web.order.entity.Order;
import com.arg.smart.web.order.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AverageVo {
    private Order order;
    private List<OrderDetail> orderDetails;
    private ProductCirculationData productCirculationData;
}

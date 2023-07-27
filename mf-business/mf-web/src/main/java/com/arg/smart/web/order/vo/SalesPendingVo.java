package com.arg.smart.web.order.vo;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Author GlowingTree
 * @Description 价格指数类值对象
 * @Date 7/13/2023 8:19 PM
 * @Version 1.0
 */
@Data
@ToString
@Builder
public class SalesPendingVo {
	Integer flag;
	BigDecimal price;
	BigDecimal lastPrice;
	BigDecimal changePrice;
	BigDecimal lifting;
	Integer pending;
}

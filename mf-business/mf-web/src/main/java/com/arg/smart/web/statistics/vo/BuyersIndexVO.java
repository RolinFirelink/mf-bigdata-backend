package com.arg.smart.web.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @Author GlowingTree
 * @Description 采购商指数值对象
 * @Date 7/29/2023 11:40 PM
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "采购商指数", description = "采购商指数")
@Builder
public class BuyersIndexVO {
	@ApiModelProperty("产品类型")
	private Integer flag;
	@ApiModelProperty("年份")
	private Integer year;
	@ApiModelProperty(value = "月份")
	private Integer month;
	@ApiModelProperty("采购商指数")
	private Double index;
}

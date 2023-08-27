package com.arg.smart.web.report.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Author GlowingTree
 * @Description 农产品产销形式采购价格值对象
 * @Date 8/24/2023 9:02 PM
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PurchaseVO {
	@ApiModelProperty("产品名称")
	private String name;
	@ApiModelProperty("产品采购平均价格")
	private BigDecimal avgPrice;
}

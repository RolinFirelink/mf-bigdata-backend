package com.arg.smart.web.report.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Author GlowingTree
 * @Description 农产品运输值对象
 * @Date 8/24/2023 6:21 PM
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CirculationVO {
	@ApiModelProperty("承运商 ID")
	private Long companyId;
	@ApiModelProperty("承运公司名称")
	private String companyName;
	@ApiModelProperty("总运量")
	private BigDecimal amount;
	@ApiModelProperty("运送总价")
	private BigDecimal price;
}

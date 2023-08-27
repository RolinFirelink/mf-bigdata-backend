package com.arg.smart.web.report.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Author GlowingTree
 * @Description 产品供需值对象
 * @Date 8/24/2023 7:23 PM
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SupplyAndDemandVO {
	@ApiModelProperty("区别字段")
	private Integer flag;
	@ApiModelProperty("供应量")
	private BigDecimal supply;
	@ApiModelProperty("需求量")
	private BigDecimal demand;
	@ApiModelProperty("单位")
	private String unit;
}

package com.arg.smart.web.report.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

/**
 * @Author GlowingTree
 * @Description 基地生产数据值对象
 * @Date 8/24/2023 4:19 PM
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseProductVO {
	@ApiModelProperty("基地 ID")
	private Long id;
	@ApiModelProperty("基地名称")
	private String name;
	@ApiModelProperty("基地产量")
	private BigDecimal amount;
}

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
 * @Description 时间日期类值对象
 * @Date 8/9/2023 1:55 PM
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "时间日期数据", description = "时间日期数据")
@Builder
public class DateVO {
	@ApiModelProperty("年份")
	private Integer year;
	@ApiModelProperty("月份")
	private Integer month;
	@ApiModelProperty("日期")
	private Integer day;
}

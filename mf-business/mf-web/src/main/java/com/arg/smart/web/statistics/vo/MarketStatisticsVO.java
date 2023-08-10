package com.arg.smart.web.statistics.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * @author cgli
 * @description: 市场行情数据值对象
 * @date: 2023-07-19
 * @version: V1.0.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "市场行情数据", description = "市场行情数据")
public class MarketStatisticsVO {
	@ApiModelProperty(value = "产地名")
	private String city;
	@ApiModelProperty(value = "纬度")
	private Double latitude;
	@ApiModelProperty(value = "经度")
	private Double longitude;
	@ApiModelProperty(value = "产量")
	private Long yield;
	@ApiModelProperty(value = "产量单位")
	private String yieldUnit;
	@ApiModelProperty(value = "销售额")
	private Double sales;
	@ApiModelProperty(value = "销售额单位")
	private String salesUnit;
	@ApiModelProperty(value = "平均价格")
	private Double avgPrice;
}

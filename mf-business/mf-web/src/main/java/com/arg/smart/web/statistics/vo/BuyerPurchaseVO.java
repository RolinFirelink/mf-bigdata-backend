package com.arg.smart.web.statistics.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author GlowingTree
 * @Description 采购商某月采购数据值对象
 * @Date 7/31/2023 5:12 PM
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ApiModel(value = "采购商某月采购数据", description = "采购商某月采购数据")
@Builder
public class BuyerPurchaseVO {
	@ApiModelProperty("采购商名称")
	private String publisher;
	@ApiModelProperty("产品名称")
	private String name;
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM")
	@DateTimeFormat(pattern = "yyyy-MM")
	private Date publishTime;
	@ApiModelProperty("采购数量")
	private Double num;
	@ApiModelProperty("采购轮次")
	private Integer round;
	@ApiModelProperty("本月采购总量")
	private Double total;
}

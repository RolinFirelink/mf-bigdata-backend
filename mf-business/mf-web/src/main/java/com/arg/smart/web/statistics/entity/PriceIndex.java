package com.arg.smart.web.statistics.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @Author GlowingTree
 * @Description 采购商价格指数表
 * @Date 8/9/2023 12:19 PM
 * @Version 1.0
 */
@Data
@TableName("sh_price_index")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_price_index对象", description = "采购商价格指数表")
@Builder
public class PriceIndex extends BaseEntity<Long> {
	@ApiModelProperty(value = "唯一ID")
	@TableId(type = IdType.AUTO)
	private Long id;
	@ApiModelProperty(value = "区分字段")
	private Integer flag;
	@ApiModelProperty(value = "采购商名称")
	private String name;
	@ApiModelProperty(value = "价格指数")
	private Double priceIndex;
	@ApiModelProperty(value = "年份")
	private Integer year;
	@ApiModelProperty(value = "月份")
	private Integer month;
	@ApiModelProperty(value = "记录时间")
	private Date recordTime;
}

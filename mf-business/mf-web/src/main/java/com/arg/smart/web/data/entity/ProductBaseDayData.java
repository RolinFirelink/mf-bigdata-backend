package com.arg.smart.web.data.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 产品基地每日数据
 * @author cgli
 * @date: 2023-07-18
 * @version: V1.0.0
 */
@Data
@TableName("sh_product_base_day_data")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_product_base_day_data对象", description = "产品基地每日数据")
public class ProductBaseDayData extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "基地ID")
    private Long baseId;
    @ApiModelProperty(value = "日期")
	private Date time;
    @ApiModelProperty(value = "产品类型")
	private Integer flag;
    @ApiModelProperty(value = "产品")
	private String product;
    @ApiModelProperty(value = "供应量")
	private BigDecimal supply;
    @ApiModelProperty(value = "供应量单位")
    private String unit;
    @ApiModelProperty(value = "产量")
	private BigDecimal yield;
    @ApiModelProperty(value = "产量单位")
	private String yieldUnit;
    @ApiModelProperty(value = "销售额")
	private BigDecimal sales;
    @ApiModelProperty(value = "销售额单位")
	private String salesUnit;
    @ApiModelProperty(value = "逻辑删除")
    private Integer deleteFlag;
}

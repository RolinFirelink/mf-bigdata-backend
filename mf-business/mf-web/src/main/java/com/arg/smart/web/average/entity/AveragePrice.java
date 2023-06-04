package com.arg.smart.web.average.entity;

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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 均价表
 * @author cgli
 * @date: 2023-06-01
 * @version: V1.0.0
 */
@Data
@TableName("sh_average_price")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_average_price对象", description = "均价表")
public class AveragePrice extends BaseEntity<Long> implements Serializable {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "产品id")
	private Long materialId;
    @ApiModelProperty(value = "区分字段")
	private Integer flag;
    @ApiModelProperty(value = "均价")
	private BigDecimal price;
    @ApiModelProperty(value = "均价单位")
	private String unit;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "均价日期")
	private Date averageDate;
    @ApiModelProperty(value = "均价地区")
	private String place;
    @ApiModelProperty(value = "逻辑删除")
	private Integer deleteFlag;
}

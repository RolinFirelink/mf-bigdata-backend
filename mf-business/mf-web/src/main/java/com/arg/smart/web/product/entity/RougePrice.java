package com.arg.smart.web.product.entity;

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
 * @description: 肉鸽价格表
 * @author cgli
 * @date: 2023-07-31
 * @version: V1.0.0
 */
@Data
@TableName("sh_rouge_price")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_rouge_price对象", description = "肉鸽价格表")
public class RougePrice extends BaseEntity<Integer> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "日期")
	private Date date;
    @ApiModelProperty(value = "产地")
	private String region;
    @ApiModelProperty(value = "价格")
	private BigDecimal price;
    @ApiModelProperty(value = "日龄")
	private String day;
    @ApiModelProperty(value = "单位")
	private String unit;
}

package com.arg.smart.web.data.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 销售地表
 * @author cgli
 * @date: 2023-08-18
 * @version: V1.0.0
 */
@Data
@TableName("sh_place_of_sale")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_place_of_sale对象", description = "销售地表")
public class PlaceOfSale extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "销售地")
	private String placeOfSale;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "记录日期")
	private Date recordDate;
    @ApiModelProperty(value = "区分字段")
	private Integer flag;
    @ApiModelProperty(value = " 行政区域编码")
	private String areaCode;
    @ApiModelProperty(value = "纬度")
	private BigDecimal lat;
    @ApiModelProperty(value = "经度")
	private BigDecimal lng;
    @TableField(exist = false)
    @ApiModelProperty(value = "行政区域名称")
    private String areaCodeName;
}

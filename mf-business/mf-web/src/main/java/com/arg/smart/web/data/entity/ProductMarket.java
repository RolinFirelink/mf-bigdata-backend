package com.arg.smart.web.data.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * @description: 批发市场表
 * @author cgli
 * @date: 2023-08-18
 * @version: V1.0.0
 */
@Data
@TableName("sh_product_market")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_product_market对象", description = "批发市场表")
public class ProductMarket extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "批发市场")
	private String market;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "记录日期")
	private Date recordDate;
    @ApiModelProperty(value = "区分字段")
	private Integer flag;
    @ApiModelProperty(value = "行政区域编码")
	private String areaCode;
    @ApiModelProperty(value = "纬度")
	private BigDecimal lat;
    @ApiModelProperty(value = "经度")
	private BigDecimal lng;
    @ApiModelProperty(value = "逻辑删除")
	private Integer deleteFlag;
    @TableField(exist = false)
    @ApiModelProperty(value = "行政区域名称")
    private String areaCodeName;
}

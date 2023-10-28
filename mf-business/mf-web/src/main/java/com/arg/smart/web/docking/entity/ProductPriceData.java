package com.arg.smart.web.docking.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
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
 * @description: 惠农网产品价格
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Data
@TableName("cn_market")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "cn_market对象", description = "惠农网产品价格")
public class ProductPriceData extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "产品名")
	private String product;
    @ApiModelProperty(value = "产品类别")
	private Integer flag;
    @ApiModelProperty(value = "平均价格")
	private BigDecimal price;
    @ApiModelProperty(value = "最高价格")
	private BigDecimal maxPrice;
    @ApiModelProperty(value = "最低价格")
	private BigDecimal minPrice;
    @ApiModelProperty(value = "单位")
	private String unit;
    @ApiModelProperty(value = "地区")
	private String region;
    @ApiModelProperty(value = "省份")
	private String provinceName;
    @ApiModelProperty(value = "城市")
	private String cityName;
    @ApiModelProperty(value = "区")
	private String areaName;
    @ApiModelProperty(value = "日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date collectdate;
    @ApiModelProperty(value = "逻辑删除")
	private Integer deleteFlag;
}

package com.arg.smart.web.price.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-12
 * @version: V1.0.0
 */
@Data
@TableName("sh_product_price")
@Accessors(chain = true)
@ApiModel(value = "sh_product_price对象", description = "产品价格表")
public class ProductPrice {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "日期")
	private Date time;
    @ApiModelProperty(value = "产品")
	private String product;
    @ApiModelProperty(value = "地区（广东广州市番禺区）")
	private String region;
    @ApiModelProperty(value = "价格（xx元/一株）（xx元/斤）")
	private BigDecimal price;
    @ApiModelProperty(value = "涨幅")
	private String lifting;
    @ApiModelProperty(value = "关联产品")
	private Integer flag;
    @ApiModelProperty(value = "计量单位")
	private String unit;
}

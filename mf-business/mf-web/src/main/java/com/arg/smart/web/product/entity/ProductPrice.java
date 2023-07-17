package com.arg.smart.web.product.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-01
 * @version: V1.0.0
 */
@Data
@TableName("sh_product_price")
@Accessors(chain = true)
@ApiModel(value = "sh_product_price对象", description = "产品价格表")
public class ProductPrice{
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "日期")
	private Date time;
    @ApiModelProperty(value = "产品")
	private String product;
    @ApiModelProperty(value = "地区（广东广州市番禺区）")
	private String region;
    @ApiModelProperty(value = "价格（xx元/一株）（xx元/斤）")
	private BigDecimal price;
    @ApiModelProperty(value = "涨幅")
	private BigDecimal lifting;
    @ApiModelProperty(value = "关联产品")
	private Integer flag;
    @ApiModelProperty(value = "计量单位")
	private String unit;
}

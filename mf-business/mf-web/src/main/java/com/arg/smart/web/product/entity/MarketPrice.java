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
 * @description: 批发市场价格
 * @author cgli
 * @date: 2023-07-09
 * @version: V1.0.0
 */
@Data
@TableName("sh_product_market_price")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_product_market_price对象", description = "批发市场价格")
public class MarketPrice extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "批发市场")
	private String market;
    @ApiModelProperty(value = "记录日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	private Date recordDate;
    @ApiModelProperty(value = "最低价格")
	private BigDecimal bottomPrice;
    @ApiModelProperty(value = "最高价格")
	private BigDecimal topPrice;
    @ApiModelProperty(value = "平均价格")
	private BigDecimal averagePrice;
    @ApiModelProperty(value = "产品名")
	private String name;
    @ApiModelProperty(value = "区分字段")
	private Integer flag;
    @ApiModelProperty(value = "计量单位")
	private String unit;
}

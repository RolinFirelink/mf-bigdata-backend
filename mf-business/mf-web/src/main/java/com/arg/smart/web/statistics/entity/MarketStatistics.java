package com.arg.smart.web.statistics.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description: 市场行情统计表
 * @author cgli
 * @date: 2023-07-17
 * @version: V1.0.0
 */
@Data
@TableName("sh_market_statistics")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_market_statistics对象", description = "市场行情统计表")
public class MarketStatistics extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "")
	private String city;
    @ApiModelProperty(value = "产量")
	private Integer yield;
    @ApiModelProperty(value = "产量单位")
	private String yieldUnit;
    @ApiModelProperty(value = "平均价格（元）")
	private BigDecimal averagePrice;
    @ApiModelProperty(value = "销售额（万元）")
	private BigDecimal sales;
    @ApiModelProperty(value = "逻辑删除")
	private Integer deleteFlag;
    @ApiModelProperty(value = "产品类型")
	private Integer flag;
}

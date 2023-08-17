package com.arg.smart.web.statistics.entity;

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

import java.util.Date;

/**
 * @description: 产品供需统计表
 * @author cgli
 * @date: 2023-07-17
 * @version: V1.0.0
 */
@Data
@TableName("sh_product_supply_demand_statistics")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_product_supply_demand_statistics对象", description = "产品供需统计表")
public class ProductSupplyDemandStatistics extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "产品")
	private String product;
    @ApiModelProperty(value = "供应")
	private Integer supply;
    @ApiModelProperty(value = "需求")
	private Integer demand;
    @ApiModelProperty(value = "单位")
	private String unit;
    @ApiModelProperty(value = "产品类型")
	private Integer flag;
    @ApiModelProperty(value = "逻辑删除")
	private Integer deleteFlag;
    @ApiModelProperty(value = "日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date statisticsTime;
}

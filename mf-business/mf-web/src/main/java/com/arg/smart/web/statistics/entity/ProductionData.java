package com.arg.smart.web.statistics.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
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
 * @description: 地区生产宏观数据
 * @author cgli
 * @date: 2023-10-19
 * @version: V1.0.0
 */
@Data
@TableName("sh_production_data")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_production_data对象", description = "地区生产宏观数据")
public class ProductionData extends BaseEntity<Integer> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "区分产品")
	private Integer flag;
    @ApiModelProperty(value = "城市")
	private String city;
    @ApiModelProperty(value = "上市时间")
	private String toMarketTime;
    @ApiModelProperty(value = "产品")
	private String product;
    @ApiModelProperty(value = "产量")
	private BigDecimal value;
    @ApiModelProperty(value = "第二产量值")
	private BigDecimal value2;
    @ApiModelProperty(value = "产值")
    private BigDecimal money;
    @ApiModelProperty(value = "生产面积")
	private BigDecimal area;
    @ApiModelProperty(value = "区域")
	private String region;
    @ApiModelProperty(value = "年份")
	private Integer year;

    @ApiModelProperty(value = "主要产品")
    private String mainProduct;
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
	private Integer deleteFlag;
}

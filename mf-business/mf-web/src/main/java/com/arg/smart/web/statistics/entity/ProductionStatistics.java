package com.arg.smart.web.statistics.entity;

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
import java.util.Date;

/**
 * @description: 生产统计
 * @author cgli
 * @date: 2023-07-15
 * @version: V1.0.0
 */
@Data
@TableName("sh_production_statistics")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_production_statistics对象", description = "生产统计")
public class ProductionStatistics extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "生产规模")
	private Integer produceScale;
    @ApiModelProperty(value = "产量")
	private String yield;
    @ApiModelProperty(value = "统计时间")
	private Date statisticalTime;
    @ApiModelProperty(value = "产量类型")
	private Integer flag;
    @ApiModelProperty(value = "月份")
	private Integer month;
    @ApiModelProperty(value = "年份")
    private Integer year;
    @ApiModelProperty(value = "产量单位")
    private String yieldUnit;
}

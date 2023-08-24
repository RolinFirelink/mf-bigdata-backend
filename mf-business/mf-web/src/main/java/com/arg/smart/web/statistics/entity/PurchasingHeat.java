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
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 采购热度
 * @author cgli
 * @date: 2023-08-24
 * @version: V1.0.0
 */
@Data
@TableName("sh_purchasing_heat")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_purchasing_heat对象", description = "采购热度")
public class PurchasingHeat extends BaseEntity<Integer> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Integer id;
    @ApiModelProperty(value = "产品")
	private String product;
    @ApiModelProperty(value = "产品类型")
	private Integer flag;
    @ApiModelProperty(value = "时间")
	private Date time;
    @ApiModelProperty(value = "采购热度")
	private BigDecimal purchasingHeat;
    @ApiModelProperty(value = "")
	private Integer deleteFlag;
}

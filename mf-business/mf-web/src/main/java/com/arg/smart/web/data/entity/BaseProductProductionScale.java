package com.arg.smart.web.data.entity;

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
 * @description: 基地产品生产规模数据表
 * @author cgli
 * @date: 2023-07-20
 * @version: V1.0.0
 */
@Data
@TableName("sh_base_product_production_scale")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_base_product_production_scale对象", description = "基地产品生产规模数据表")
public class BaseProductProductionScale extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "生产规模")
	private BigDecimal productionScale;
    @ApiModelProperty(value = "基地ID")
	private Long baseId;
    @ApiModelProperty(value = "规模单位")
	private String scaleUnit;
    @ApiModelProperty(value = "产品")
	private String product;
    @ApiModelProperty(value = "产品类型")
	private Integer flag;
    @ApiModelProperty(value = "统计日期")
	private Date statisticalTime;
    @ApiModelProperty(value = "逻辑删除")
	private Integer deleteFlag;
}

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

/**
 * @description: 省份供应表
 * @author cgli
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Data
@TableName("sh_province_supply")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_province_supply对象", description = "省份供应表")
public class ProvinceSupply extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "省份")
	private String province;
    @ApiModelProperty(value = "供应量单位")
	private String upplyUnit;
    @ApiModelProperty(value = "供应量类型")
	private Integer flag;
    @ApiModelProperty(value = "产量")
	private String upply;
    @ApiModelProperty(value = "产品")
	private String product;
}

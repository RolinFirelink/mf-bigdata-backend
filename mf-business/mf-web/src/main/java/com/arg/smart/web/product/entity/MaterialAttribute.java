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

/**
 * @description: 产品属性表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Data
@TableName("sh_material_attribute")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_material_attribute对象", description = "产品属性表")
public class MaterialAttribute extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "属性字段")
	private String attributeField;
    @ApiModelProperty(value = "属性名")
	private String attributeName;
    @ApiModelProperty(value = "属性值")
	private String attributeValue;
    @ApiModelProperty(value = "归属组织id")
	private Long orgId;
    @ApiModelProperty(value = "0--未删除 1--已删除 DIC_NAME=DELETE_FLAG")
	private Integer deletedFlag;
    @ApiModelProperty(value = "区分字段")
	private Integer flag;
}

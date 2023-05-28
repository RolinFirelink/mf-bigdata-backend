package com.arg.smart.web.product.entity;

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

/**
 * @description: 品牌产品中间表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Data
@TableName("sh_material_brand_record")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_material_brand_record对象", description = "品牌产品中间表")
public class MaterialBrandRecord extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "品牌id")
	private Long brandId;
    @ApiModelProperty(value = "产品id")
	private Long materialId;

    @ApiModelProperty(value = "产品名")
    @TableField(exist = false)
    private String materialName;

    @ApiModelProperty(value ="品牌名")
    @TableField(exist = false)
    private String brandName;
}

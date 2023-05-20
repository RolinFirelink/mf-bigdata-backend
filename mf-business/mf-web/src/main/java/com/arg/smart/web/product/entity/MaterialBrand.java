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

import java.util.Date;

/**
 * @author cgli
 * @description: 产品品牌表
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Data
@TableName("sh_material_brand")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_material_brand对象", description = "产品品牌表")
public class MaterialBrand extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "逻辑删除")
    private Integer deletedFlag;
    @ApiModelProperty(value = "品牌名")
    private String name;
    @ApiModelProperty(value = "品牌描述")
    private String description;
    @ApiModelProperty(value = "品牌价值观")
    private String values;
    @ApiModelProperty(value = "品牌官网")
    private String website;
    @ApiModelProperty(value = "品牌创立时间")
    private Date establishedDate;
    @ApiModelProperty(value = "区分字段")
    private Integer flag;
    @ApiModelProperty(value = "品牌归属公司名")
    private String companyName;
    @ApiModelProperty(value = "品牌归属公司id")
    private Long companyId;
}

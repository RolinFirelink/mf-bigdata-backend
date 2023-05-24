package com.arg.smart.web.product.entity;

import com.arg.smart.common.core.entity.BaseTreeEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description: 产品类型表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Data
@TableName("sh_material_category")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_material_category对象", description = "产品类型表")
public class MaterialCategory extends BaseTreeEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "名称")
	private String name;
    @ApiModelProperty(value = "编号")
	private String serialNo;
    @ApiModelProperty(value = "等级")
	private Integer categoryLevel;
    @ApiModelProperty(value = "上级id")
	private Long parentId;
    @ApiModelProperty(value = "显示顺序")
	private String sort;
    @ApiModelProperty(value = "备注")
	private String remark;
    @ApiModelProperty(value = "0--未删除 1--已删除 DIC_NAME=DELETE_FLAG")
	private Integer deletedFlag;
    @ApiModelProperty(value = "归属组织id")
	private Long orgId;
    @ApiModelProperty(value = "区分字段")
	private Integer flag;
}

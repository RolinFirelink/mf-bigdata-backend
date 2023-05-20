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
 * @description: 产品库存表
 * @author cgli
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Data
@TableName("sh_material_storage")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_material_storage对象", description = "产品库存表")
public class MaterialStorage extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "区分字段")
	private Integer flag;
    @ApiModelProperty(value = "自定义拓展JSON结构数据")
	private String data;
    @ApiModelProperty(value = "逻辑删除")
	private Integer deletedFlag;
    @ApiModelProperty(value = "归属组织id")
	private Long ownerId;
    @ApiModelProperty(value = "产品id")
	private Long materialId;
    @ApiModelProperty(value = "产品名字")
	private String materialName;
    @ApiModelProperty(value = "库存数量")
	private Integer number;
}

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

import java.math.BigDecimal;

/**
 * @description: 产品表
 * @author cgli
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Data
@TableName("sh_material")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_material对象", description = "产品表")
public class Material extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "产品类型id")
	private Long categoryId;
    @ApiModelProperty(value = "产品名称")
	private String name;
    @ApiModelProperty(value = "型号")
	private String model;
    @ApiModelProperty(value = "规格")
	private String standard;
    @ApiModelProperty(value = "颜色")
	private String color;
    @ApiModelProperty(value = "单位")
	private String unit;
    @ApiModelProperty(value = "备注")
	private String remark;
    @ApiModelProperty(value = "保质期天数")
	private Integer expiryNum;
    @ApiModelProperty(value = "基础重量(kg)")
	private BigDecimal weight;
    @ApiModelProperty(value = "启用 0-禁用  1-启用")
	private Integer enabled;
    @ApiModelProperty(value = "自定义扩展字段JSON结构")
	private String extendField;
    @ApiModelProperty(value = "是否开启序列号，0否，1是")
	private Integer enableSerialNumber;
    @ApiModelProperty(value = "是否开启批号，0否，1是")
	private Integer enableBatchNumber;
    @ApiModelProperty(value = "0--未删除 1--已删除 DIC_NAME=DELETE_FLAG")
	private Integer deletedFlag;
    @ApiModelProperty(value = "归属组织id")
	private Long orgId;
    @ApiModelProperty(value = "种植规模(单位为㎡)")
	private Integer scale;
    @ApiModelProperty(value = "区分字段")
	private Integer flag;
}

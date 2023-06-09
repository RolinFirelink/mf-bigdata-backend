package com.arg.smart.web.company.entity;

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
 * @description: 产品基地
 * @author cgli
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Data
@TableName("sh_product_base")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_product_base对象", description = "产品基地")
public class ProductBase extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "基地名称")
	private String baseName;
    @ApiModelProperty(value = "基地编码")
	private String baseNo;
    @ApiModelProperty(value = "联系人")
	private String contacts;
    @ApiModelProperty(value = "联系电话")
	private String contactPhone;
    @ApiModelProperty(value = "电子邮箱")
	private String email;
    @ApiModelProperty(value = "行政区域编码")
	private String areaCode;
    @ApiModelProperty(value = "基地详细地址")
	private String address;
    @ApiModelProperty(value = "1=已删除、0=未删除")
	private Integer deleteFlag;
    @ApiModelProperty(value = "外键，关联企业表，一个养殖基地只能对应一个企业")
	private Long companyId;
    @ApiModelProperty(value = "自定义拓展字段JSON 结构")
	private String extendField;
    @ApiModelProperty(value = "备注")
	private String remark;
}

package com.arg.smart.web.customer.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description: 客户表
 * @author cgli
 * @date: 2023-05-25
 * @version: V1.0.0
 */
@Data
@TableName("sh_customer")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_customer对象", description = "客户表")
public class Customer extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "客户名称")
	private String name;
    @ApiModelProperty(value = "客户年龄")
    @TableField(value = "`age`")
    private Integer age;
    @ApiModelProperty(value = "客户性别")
    @TableField(value = "`gender`")
    private Integer gender;
    @ApiModelProperty(value = "职业")
    @TableField(value = "`occupation`")
    private Integer occupation;
    @ApiModelProperty(value = "城市")
    @TableField(value = "`city`")
    private Integer city;
    @ApiModelProperty(value = "关联客户编号")
	private Long platformUserId;
    @ApiModelProperty(value = "第三方平台ID")
	private Long platformId;
    @ApiModelProperty(value = "产品分类")
	private Integer flag;
    @ApiModelProperty(value = "备注")
	private String remark;
    @TableLogic
	private Integer deleteFlag;
}

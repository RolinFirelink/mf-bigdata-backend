package com.arg.smart.web.customer.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
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
    @ApiModelProperty(value = "年龄")
    private Integer age;
    @ApiModelProperty(value = "性别")
    private Integer gender;
    @ApiModelProperty(value = "职业")
    private String occupation;
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "关联客户编号")
	private Long platformUserId;
    @ApiModelProperty(value = "第三方平台ID")
	private Long platformId;
    @ApiModelProperty(value = "产品分类")
	private Integer flag;
    @ApiModelProperty(value = "备注")
	private String remark;
    @ApiModelProperty(value = "版本号")
    private Integer version;
    @TableLogic
	private Integer deleteFlag;
}

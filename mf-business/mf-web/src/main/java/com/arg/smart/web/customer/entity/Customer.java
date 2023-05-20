package com.arg.smart.web.customer.entity;

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
 * @description: 客户表
 * @author cgli
 * @date: 2023-05-17
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
    @ApiModelProperty(value = "")
	private Integer deleteFlag;
    @ApiModelProperty(value = "客户名称")
	private String name;
    @ApiModelProperty(value = "平台id")
	private Long platformId;
    @ApiModelProperty(value = "第三方用户id")
	private Long platformUserId;
    @ApiModelProperty(value = "乐观锁")
	private Long version;
}

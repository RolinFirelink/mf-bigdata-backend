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
 * @description: 客户消费行为表
 * @author cgli
 * @date: 2023-05-25
 * @version: V1.0.0
 */
@Data
@TableName("sh_customer_behavior")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_customer_behavior对象", description = "客户消费行为表")
public class CustomerBehavior extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "客户编号")
	private Long customerId;
    @ApiModelProperty(value = "行为类型")
	private Integer type;
    @ApiModelProperty(value = "扩展信息")
	private String extendInfo;
    @ApiModelProperty(value = "备注")
	private String remark;
    @ApiModelProperty(value = "产品分类")
	private Integer flag;
    @TableLogic
	private Integer deleteFlag;
}

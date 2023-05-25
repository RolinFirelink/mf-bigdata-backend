package com.arg.smart.web.cargo.entity;

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
 * @description: 货运进度表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
@Data
@TableName("sh_freight_progress")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_freight_progress对象", description = "货运进度表")
public class FreightProgress extends BaseEntity<String> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    @ApiModelProperty(value = "运输状态")
	private String transportState;
    @ApiModelProperty(value = "进度地点,各个地点之间用,分隔")
	private String transportLocation;
    @ApiModelProperty(value = "货运编号")
	private Integer freightNumber;
    @ApiModelProperty(value = "0-未删除,1-已删除")
	private Integer deleteFlag;
}

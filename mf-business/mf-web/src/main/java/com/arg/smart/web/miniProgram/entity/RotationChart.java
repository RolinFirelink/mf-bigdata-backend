package com.arg.smart.web.miniProgram.entity;

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
 * @description: 轮播图信息
 * @author cgli
 * @date: 2023-06-02
 * @version: V1.0.0
 */
@Data
@TableName("sh_rotation_chart")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_rotation_chart对象", description = "轮播图信息")
public class RotationChart extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "图片地址")
	private String imgUrl;
    @ApiModelProperty(value = "跳转路径")
	private String path;
    @ApiModelProperty(value = "顺序")
	private Integer sort;
    @ApiModelProperty(value = "状态（是否启用）")
	private Integer status;
    @ApiModelProperty(value = "拓展字段")
	private String extendAttribute;
}

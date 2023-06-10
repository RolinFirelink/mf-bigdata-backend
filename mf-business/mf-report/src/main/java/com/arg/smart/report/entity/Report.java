package com.arg.smart.report.entity;

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
 * @description: 报表页面
 * @author cgli
 * @date: 2023-05-31
 * @version: V1.0.0
 */
@Data
@TableName("t_report_page")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "t_report_page", description = "报表页面")
public class Report extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "名称")
	private String name;
    @ApiModelProperty(value = "关联产品")
	private Integer flag;
    @ApiModelProperty(value = "封面图片")
	private String imgUrl;
    @ApiModelProperty(value = "访问地址")
	private String path;
    @ApiModelProperty(value = "类型")
    private Integer type;
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
	private Integer deleteFlag;
}

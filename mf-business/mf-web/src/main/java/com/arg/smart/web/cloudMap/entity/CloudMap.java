package com.arg.smart.web.cloudMap.entity;

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
 * @description: 云图
 * @author cgli
 * @date: 2023-08-25
 * @version: V1.0.0
 */
@Data
@TableName("sh_cloud_map")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_cloud_map对象", description = "云图")
public class CloudMap extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "产品分类")
	private Integer flag;
    @ApiModelProperty(value = "标题")
	private String title;
    @ApiModelProperty(value = "摘要")
	private String summary;
    @ApiModelProperty(value = "路由跳转路径")
	private String path;

    @ApiModelProperty(value = "图片")
    private String imgUrl;
    @ApiModelProperty(value = "排序")
	private Integer sort;
    @ApiModelProperty(value = "逻辑删除")
	private Integer deleteFlag;
}

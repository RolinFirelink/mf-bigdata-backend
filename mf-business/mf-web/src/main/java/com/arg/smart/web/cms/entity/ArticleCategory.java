package com.arg.smart.web.cms.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.arg.smart.common.core.entity.BaseTreeEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @description: 文章
 * @author cgli
 * @date: 2023-05-08
 * @version: V1.0.0
 */
@Data
@TableName("sh_article_category")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_article_category对象", description = "文章")
public class ArticleCategory extends BaseTreeEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "分类名称")
	private String name;
    @ApiModelProperty(value = "排序")
	private Integer sort;
    @ApiModelProperty(value = "文章数")
	private Integer num;
    @ApiModelProperty(value = "上级ID")
	private Long parentId;
    @ApiModelProperty(value = "一串IDS组合")
	private String parentIds;
    @ApiModelProperty(value = "一串IDS组合名称")
	private String parentNames;
    @ApiModelProperty(value = "多租户组织ID")
	private Long orgId;
    @ApiModelProperty(value = "0--未删除 1--已删除 DIC_NAME=DELETE_FLAG")
	private Integer deleteFlag;
}

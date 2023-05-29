package com.arg.smart.sys.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.arg.smart.common.core.entity.BaseTreeEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * @description: 行政区域
 * @author cgli
 * @date: 2023-05-06
 * @version: V1.0.0
 */
@Data
@TableName("sys_region")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sys_region对象", description = "行政区域")
public class Region extends BaseEntity<String> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    @ApiModelProperty(value = "父id")
	private String pid;
    @ApiModelProperty(value = "一串IDS组合")
	private String pids;
    @ApiModelProperty(value = "一串IDS组合名称")
	private String pidsName;
    @ApiModelProperty(value = "行政区划名称")
	private String name;
    @ApiModelProperty(value = "英文名称")
	private String englishName;
    @ApiModelProperty(value = "行政区划代码")
	private String code;
    @ApiModelProperty(value = "树状结构层次数")
	private Integer level;
    @ApiModelProperty(value = "行政区划级别名称")
	private String levelName;
    @ApiModelProperty(value = "国家ID")
	private String countryId;
    @ApiModelProperty(value = "备注信息")
	private String remarks;
    @ApiModelProperty(value = "删除标记")
	private Integer deletedFlag;
    @ApiModelProperty(value = "是否是子节点")
    @TableField(exist = false)
    private Boolean isLeaf;
}

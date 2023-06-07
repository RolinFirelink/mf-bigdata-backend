package com.arg.smart.web.data.entity;

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
 * @description: sh_data_import
 * @author cgli
 * @date: 2023-06-05
 * @version: V1.0.0
 */
@Data
@TableName("sh_data_import")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_data_import对象", description = "sh_data_import")
public class DataImport extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = " 表名")
	private String tableName;
    @ApiModelProperty(value = "列1")
	private String column1;
    @ApiModelProperty(value = "列2")
	private String column2;
    @ApiModelProperty(value = "列3")
	private String column3;
    @ApiModelProperty(value = "列4")
	private String column4;
    @ApiModelProperty(value = "列5")
	private String column5;
    @ApiModelProperty(value = "列6")
	private String column6;
    @ApiModelProperty(value = "列7")
	private String column7;
    @ApiModelProperty(value = "列8")
	private String column8;
    @ApiModelProperty(value = "列9")
	private String column9;
    @ApiModelProperty(value = "列10")
	private String column10;
    @ApiModelProperty(value = "列11")
	private String column11;
    @ApiModelProperty(value = "列12")
	private String column12;
    @ApiModelProperty(value = "逻辑删除")
    private Integer deleteFlag;
}

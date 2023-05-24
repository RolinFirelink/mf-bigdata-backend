package com.arg.smart.web.product.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 产品品牌表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Data
@TableName("sh_material_brand")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_material_brand对象", description = "产品品牌表")
public class MaterialBrand extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "逻辑删除")
	private Integer deletedFlag;
    @ApiModelProperty(value = "品牌名")
	private String name;
    @ApiModelProperty(value = "品牌描述")
	private String description;
    @ApiModelProperty(value = "品牌价值观")
	private String companyValue;
    @ApiModelProperty(value = "品牌官网")
	private String companyWebsite;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "品牌创立时间")
	private Date establishedDate;
    @ApiModelProperty(value = "区分字段")
	private Integer flag;
    @ApiModelProperty(value = "品牌归属公司名")
	private String companyName;
    @ApiModelProperty(value = "品牌归属公司id")
	private Long companyId;
}

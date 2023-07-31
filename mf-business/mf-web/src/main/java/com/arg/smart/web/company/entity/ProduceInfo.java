package com.arg.smart.web.company.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 企业生产信息表
 * @author cgli
 * @date: 2023-07-11
 * @version: V1.0.0
 */
@Data
@TableName("sh_produce_info")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_produce_info对象", description = "企业生产信息表")
public class ProduceInfo extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "企业ID")
	private Long companyId;
    @ApiModelProperty(value = "产品类别")
	private Integer flag;
    @ApiModelProperty(value = "均价")
	private BigDecimal price;
    @ApiModelProperty(value = "生产规模")
	private Integer scale;
    @ApiModelProperty(value = "生产规模单位")
	private String scaleUnit;
    @ApiModelProperty(value = "价格单位")
	private String priceUnit;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "预计上市时间")
	private Date estimatedLaunchDate;
    @ApiModelProperty(value = "标识（多个，用分号分割）")
	private String identifiers;
    @TableField(exist = false)
    @ApiModelProperty(value = "公司名")
    private String companyName;
    @ApiModelProperty(value = "产品名称")
    private String product;
}

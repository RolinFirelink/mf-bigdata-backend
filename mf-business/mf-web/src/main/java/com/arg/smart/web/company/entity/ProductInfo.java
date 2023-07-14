package com.arg.smart.web.company.entity;

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

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 产品基地
 * @author cgli
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Data
@TableName("sh_produce_info")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_produce_info对象", description = "产品企业和经纬度信息")
public class ProductInfo extends BaseEntity<Long> {

    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "企业ID")
    private Long companyId;
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
    @ApiModelProperty(value = "上报人（创建人）")
    private String  createBy ;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "上报时间")
    private Date  createTime ;
    @ApiModelProperty(value = "修改人")
    private String  updateBy ;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "修改时间")
    private Date  updateTime ;
    @ApiModelProperty(value = "预计产量")
    private String  projectedProduction ;
    @ApiModelProperty(value = "产量单位")
    private String  productUnit ;

}

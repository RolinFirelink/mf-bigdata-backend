package com.arg.smart.web.data.entity;


import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.EqualsAndHashCode;


/**
 * @description: 公司营收
 * @author cgli
 * @date: 2023-10-29
 * @version: V1.0.0
 */
@Data
@TableName("sh_company_revenue")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_company_revenue对象", description = "公司营收")
public class CompanyRevenue extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "区分字段")
	private Integer flag;
    @ApiModelProperty(value = "公司名称")
	private String companyName;
    @ApiModelProperty(value = "公司营收")
	private BigDecimal companyRevenue;
    @ApiModelProperty(value = "年份")
	private Integer year;
}

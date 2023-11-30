package com.arg.smart.web.data.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CompanyRevenueVO{
    @ApiModelProperty(value = "公司名称")
	private String companyName;
    @ApiModelProperty(value = "公司营收")
	private BigDecimal companyRevenue;
    @ApiModelProperty(value = "年份")
	private Integer year;
}

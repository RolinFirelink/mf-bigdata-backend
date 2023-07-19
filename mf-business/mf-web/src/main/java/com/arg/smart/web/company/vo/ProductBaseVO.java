package com.arg.smart.web.company.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductBaseVO {

    @ApiModelProperty("手机号")
    private String iphoneNumber;

    @ApiModelProperty("基地名称")
    private String baseName;

    @ApiModelProperty("纬度")
    private String lat;

    @ApiModelProperty("经度")
    private String lng;

    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("年产量")
    private BigDecimal annualOutput;

    @ApiModelProperty("主要产品")
    private String mainProduct;

    @ApiModelProperty("产量单位")
    private String outputUnit;

    @ApiModelProperty("区")
    private String region;

}
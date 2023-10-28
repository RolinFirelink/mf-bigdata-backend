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

    @ApiModelProperty("联系人")
    private String contacts;

    @ApiModelProperty("手机号")
    private String iphoneNumber;

    @ApiModelProperty("基地名称")
    private String baseName;

    @ApiModelProperty("纬度")
    private BigDecimal lat;

    @ApiModelProperty("经度")
    private BigDecimal lng;

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

    @ApiModelProperty("上市时间")
    private String timeToMarket;

    @ApiModelProperty(value ="基地面积")
    private Integer area;

    @ApiModelProperty(value = "公司网址")
    private String websiteAddress;
}
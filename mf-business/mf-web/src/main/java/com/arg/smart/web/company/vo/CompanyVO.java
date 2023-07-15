package com.arg.smart.web.company.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompanyVO {

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "经纬度")
    private double[] latLongEtude;

    @ApiModelProperty(value = "所在区")
    private  String domicile;

    @ApiModelProperty(value = "联系方式")
    private  String contact;

    @ApiModelProperty(value = "主要产品")
    private String mainProduct;

    @ApiModelProperty(value = "年产量")
    private String annualOutput;
}

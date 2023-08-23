package com.arg.smart.web.company.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 企业、供货商、销售商和承运商
 * @author lbz
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("企业、供货商、销售商和承运商请求参数")
public class ReqCompany {

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value = "公司类型(1=供货商、2=销售商、3=承运商）")
    private Integer companyType;

    @ApiModelProperty(value = "联系人")
    private String contacts;

    @ApiModelProperty(value = "联系电话")
    private String contactPhone;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "城市")
    private String city;

    @ApiModelProperty(value = "地区")
    private String region;

    @ApiModelProperty(value = "所在分类的名称(例如：供应商名称 )")
    private String nameOfClassification;

    @ApiModelProperty(value = "业务范围")
    private String businessScope;

    @ApiModelProperty(value = "详细地址")
    private String address;

}

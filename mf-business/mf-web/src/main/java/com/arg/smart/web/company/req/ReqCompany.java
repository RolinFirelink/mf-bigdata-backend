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

}

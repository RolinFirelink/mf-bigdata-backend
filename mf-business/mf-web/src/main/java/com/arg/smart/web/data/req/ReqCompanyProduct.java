package com.arg.smart.web.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 公司产品
 * @author cgli
 * @date: 2023-10-29
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("公司产品请求参数")
public class ReqCompanyProduct {

    @ApiModelProperty(value = "公司名称")
    private String companyName;

    @ApiModelProperty(value =  "产品名称")
    private String product;

    @ApiModelProperty(value = "产品类别")
    private Integer flag;
}

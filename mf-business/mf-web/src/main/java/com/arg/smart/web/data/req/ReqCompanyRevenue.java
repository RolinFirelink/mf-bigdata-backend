package com.arg.smart.web.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 公司营收
 * @author cgli
 * @date: 2023-10-29
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("公司营收请求参数")
public class ReqCompanyRevenue {

    @ApiModelProperty("产品类型")
    private Integer flag;

    @ApiModelProperty("公司名称")
    private String companyName;

    @ApiModelProperty("年份")
    private Integer year;

}

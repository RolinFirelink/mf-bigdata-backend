package com.arg.smart.web.product.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-01
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("产品价格表请求参数")
public class ReqProductPrice {
    @ApiModelProperty(value = "区分字段")
    private Integer flag;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "产品地区")
    private String region;

    @ApiModelProperty(value = "产品日期")
    private String time;
}

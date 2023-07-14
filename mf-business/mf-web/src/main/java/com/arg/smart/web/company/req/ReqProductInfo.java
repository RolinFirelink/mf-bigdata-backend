package com.arg.smart.web.company.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 产品基地
 * @author lwy
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("产品企业信息请求参数")
public class ReqProductInfo {

    @ApiModelProperty(value = "产品类型")
    private Integer flag;

    @ApiModelProperty(value = "市")
    private String city;


}

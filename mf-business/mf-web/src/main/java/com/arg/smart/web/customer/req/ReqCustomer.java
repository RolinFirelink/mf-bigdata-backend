package com.arg.smart.web.customer.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 客户表
 * @author cgli
 * @date: 2023-05-25
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("客户表请求参数")
public class ReqCustomer {

    @ApiModelProperty(value = "客户名称")
    private String name;

    @ApiModelProperty(value = "性别")
    private Integer gender;

}

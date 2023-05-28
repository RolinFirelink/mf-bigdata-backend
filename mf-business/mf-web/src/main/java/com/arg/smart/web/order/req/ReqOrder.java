package com.arg.smart.web.order.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 订单数据主表
 * @author cgli
 * @date: 2023-05-19
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("订单数据主表请求参数")
public class ReqOrder {
    @ApiModelProperty(value = "供应企业编号")
    private Long companyId;
    @ApiModelProperty(value = "供应企业编码")
    private String companyNo;
    @ApiModelProperty(value = "供应企业名称")
    private String companyName;
    @ApiModelProperty(value = "订单类型")
    private Integer category;
}

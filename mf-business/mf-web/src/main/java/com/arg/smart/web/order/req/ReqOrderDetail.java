package com.arg.smart.web.order.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 订单数据明细表
 * @author cgli
 * @date: 2023-05-22
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("订单数据明细表请求参数")
public class ReqOrderDetail {
    @ApiModelProperty(value = "产品编号")
    private Long productId;
    @ApiModelProperty(value = "产品名称")
    private String productName;
}

package com.arg.smart.web.cargo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 货运表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("货运表请求参数")
public class ReqProductCirculationData {
    @ApiModelProperty(value = "订单ID")
    private Long orderId;

    @ApiModelProperty(value = "业务类型（生产、销售等）")
    private Integer businessType;

    @ApiModelProperty(value = "运输方式")
    private String modeTransport;
}

package com.arg.smart.web.cargo.entity.vo;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@ToString
@Accessors(chain = true)
public class CarrierInformation {

    @ApiModelProperty(value = "承运商名称")
    private String companyName;

    @ApiModelProperty(value = "运输总量")
    private Integer transportTotal;

    @ApiModelProperty(value = "运输订单数量")
    private Integer transportOrderNumber;

    @ApiModelProperty(value = "运输均价")
    private BigDecimal transportAvgPrice;
}

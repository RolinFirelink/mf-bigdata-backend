package com.arg.smart.web.data.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductMarketData {

    @ApiModelProperty(value = "产品")
    private String product;

    @ApiModelProperty(value = "产量")
    private BigDecimal yield;

    @ApiModelProperty(value = "销售额")
    private BigDecimal sales;
}

package com.arg.smart.web.product.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
public class AreaAvgPriceAndSales {


        @ApiModelProperty(value = "地区")
        private String region;
        @ApiModelProperty(value = "均价")
        private BigDecimal avgPrice;
        @ApiModelProperty(value = "销量")
        private Integer sales;
    }


package com.arg.smart.web.position.entity;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@ToString
@Accessors(chain = true)
public class PositionData {
    @ApiModelProperty(value = "产地、批发市场、销售地")
    private String location;

    @ApiModelProperty(value = "纬度")
    BigDecimal lat;

    @ApiModelProperty(value = "经度")
    BigDecimal lng;



}
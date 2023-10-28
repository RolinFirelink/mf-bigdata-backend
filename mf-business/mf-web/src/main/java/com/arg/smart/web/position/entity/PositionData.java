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
    private BigDecimal lat;

    @ApiModelProperty(value = "经度")
    private BigDecimal lng;


    @ApiModelProperty(value = "名字")
    private String name;

    @ApiModelProperty(value = "地址")
    private String address;

}

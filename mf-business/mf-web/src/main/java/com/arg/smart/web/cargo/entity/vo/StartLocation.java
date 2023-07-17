package com.arg.smart.web.cargo.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
@Data
@ToString
@Accessors(chain = true)
public class StartLocation {
    @ApiModelProperty(value = "起始地点")
    private String startLocation;
    @ApiModelProperty(value = "起始经度")
    private BigDecimal startLat;
    @ApiModelProperty(value = "起始纬度")
    private BigDecimal stratLon;
}

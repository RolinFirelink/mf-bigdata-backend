package com.arg.smart.web.cargo.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
@Data
@ToString
@Accessors(chain = true)
public class EndLocation {
    @ApiModelProperty(value = "销售地点")
    private String shipLocation;
    @ApiModelProperty(value = "结束经度")
    private BigDecimal endLat;
    @ApiModelProperty(value = "结束纬度")
    private BigDecimal endLon;

}

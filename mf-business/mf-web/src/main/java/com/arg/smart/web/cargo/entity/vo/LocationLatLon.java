package com.arg.smart.web.cargo.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class LocationLatLon {
    @ApiModelProperty(value = "产品类别")
    private Integer flag;
    @ApiModelProperty(value = "起始地点")
    private StartLocation startLocation;
    @ApiModelProperty(value = "销售地点")
    private EndLocation endLocation;

}

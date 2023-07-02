package com.arg.smart.web.cargo.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class TransportationProportion {
    @ApiModelProperty(value = "运输方式")
    private String name;
    @ApiModelProperty(value = "运输类型占比")
    private Double proportion;
}

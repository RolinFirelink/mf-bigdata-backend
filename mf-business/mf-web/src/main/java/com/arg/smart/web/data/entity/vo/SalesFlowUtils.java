package com.arg.smart.web.data.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class SalesFlowUtils {
    @ApiModelProperty(value = "纬度")
    private BigDecimal lat;
    @ApiModelProperty(value = "经度")
    private BigDecimal lng;
}

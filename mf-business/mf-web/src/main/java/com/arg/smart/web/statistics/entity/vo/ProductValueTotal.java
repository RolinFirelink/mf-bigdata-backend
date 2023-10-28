package com.arg.smart.web.statistics.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class ProductValueTotal {
    @ApiModelProperty("区分产品")
    private Integer flag;
    @ApiModelProperty("城市名")
    private String city;
    @ApiModelProperty("产品总产值")
    private BigDecimal productWeightTotal;
    @ApiModelProperty(value = "预计产值总值")
    private BigDecimal productValueTotal;
    @ApiModelProperty(value = "生产面积/存栏量")
    private BigDecimal productScaleTotal;
}

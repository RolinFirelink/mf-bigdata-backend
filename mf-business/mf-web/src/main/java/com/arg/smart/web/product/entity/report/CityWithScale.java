package com.arg.smart.web.product.entity.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;


@Data
@ApiModel(value = "各个市生产数据", description = "各个市生产数据")
public class CityWithScale {
    @ApiModelProperty("城市")
    private String city;

    @ApiModelProperty("生产规模")
    private BigDecimal productionScale;

    @ApiModelProperty("区分字段")
    private Integer flag;
}

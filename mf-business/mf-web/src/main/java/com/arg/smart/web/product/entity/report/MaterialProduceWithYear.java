package com.arg.smart.web.product.entity.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

//年份产品生产数据
@Data
@ApiModel(value = "年份产品生产数据", description = "年份产品生产数据")
public class MaterialProduceWithYear {

    @ApiModelProperty("种植面积")
    private BigDecimal area;

    @ApiModelProperty("产量")
    private Integer yield;

    @ApiModelProperty("年份")
    private Integer year;
}

package com.arg.smart.web.product.entity.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;

//生产基地生产数据
@Data
@ApiModel(value = "生产基地生产数据", description = "生产基地生产数据")
public class MaterialProduceWithProduceBase {

    @ApiModelProperty("种植面积")
    private BigDecimal area;

    @ApiModelProperty("产量")
    private Integer yield;

    @ApiModelProperty("基地ID")
    private Long baseId;

    @ApiModelProperty("基地名称")
    private String produceBaseName;
}

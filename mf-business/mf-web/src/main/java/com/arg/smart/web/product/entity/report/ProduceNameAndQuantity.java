package com.arg.smart.web.product.entity.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

//产品的不同种类数量
@Data
@ApiModel(value = "年份产品生产数据", description = "年份产品生产数据")
public class ProduceNameAndQuantity {
    @ApiModelProperty("产品品种名")
    String name;

    @ApiModelProperty("产量")
    Integer quantity;

    @ApiModelProperty("占比")
    String proportion;
}

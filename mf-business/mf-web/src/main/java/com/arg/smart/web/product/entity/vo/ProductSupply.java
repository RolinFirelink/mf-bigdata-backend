package com.arg.smart.web.product.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class ProductSupply {
    @ApiModelProperty("产品名")
    String productName;
    @ApiModelProperty("供应量")
    Integer supplyNumber;
    @ApiModelProperty("需求量")
    Integer demandNumber;
}

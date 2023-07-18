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
public class ProductSupply {
    @ApiModelProperty(value = "产品名称")
    private String product;

    @ApiModelProperty(value = "供应量")
    private BigDecimal supply;
}

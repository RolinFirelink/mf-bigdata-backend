package com.arg.smart.web.data.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
@Accessors(chain = true)
public class SupplyHeatResponseData {
    @ApiModelProperty(value = "产地名称")
    private String baseName;

    @ApiModelProperty(value = "产品供应量")
    private List<ProductSupply> productSupply;

    @ApiModelProperty(value = "供应量单位")
    private String unit;

    @ApiModelProperty(value = "所属城市")
    private String city;

    @ApiModelProperty(value = "经度")
    private BigDecimal lat;

    @ApiModelProperty(value = "纬度")
    private BigDecimal lng;

}

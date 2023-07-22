package com.arg.smart.web.data.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

@Data
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class BaseMarketResponseData {

    @ApiModelProperty(value = "产地名称")
    private String baseName;


    @ApiModelProperty("销售额单位")
    private String salesUnit;

    @ApiModelProperty("产量单位")
    private String yieldUnit;

    @ApiModelProperty(value = "产品行情数据")
    private List<ProductMarketData> productMarketData;

    @ApiModelProperty(value = "所属城市")
    private String city;

    @ApiModelProperty(value = "经度")
    private BigDecimal lat;

    @ApiModelProperty(value = "纬度")
    private BigDecimal lng;

    @ApiModelProperty(value = "所属区")
    private String region;
}

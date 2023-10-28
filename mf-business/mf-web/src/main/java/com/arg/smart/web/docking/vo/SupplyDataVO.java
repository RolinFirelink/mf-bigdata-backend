package com.arg.smart.web.docking.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import java.math.BigDecimal;

@Data
@Accessors(chain = true)
@ApiModel(value = "竞品供应数据", description = "竞品供应数据")
public class SupplyDataVO {
    @ApiModelProperty(value = "省份")
    private String provinceName;

    @ApiModelProperty(value = "该省占全国比重")
    private Double cntRatio;

    @ApiModelProperty(value = "平均价格")
    private BigDecimal avgPrice;
}

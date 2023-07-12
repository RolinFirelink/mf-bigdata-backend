package com.arg.smart.web.product.entity.report;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

//各个市生产数据
@Data
@ApiModel(value = "各个市生产数据", description = "各个市生产数据")
public class MaterialProduceWithCity {
    @ApiModelProperty("最大规模")
    private Integer max;

    @ApiModelProperty("最小规模")
    private Integer min;

    @ApiModelProperty("单位")
    private String unit;

    @ApiModelProperty("各个城市规模信息")
    private List<CityWithScale> city;
}

package com.arg.smart.web.product.entity.report;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ApiModel(value = "预计上市产量和上市时间", description = "预计上市产量和上市时间")
public class EstimateTimeAndMarket {
    @ApiModelProperty(value = "预计上市时间")
    private String timeEstimate;

    @ApiModelProperty("预计上市产量")
    private Integer marketEstimate;

    @ApiModelProperty("产品名字")
    private String name;

    @ApiModelProperty("预计单位产量")
    private Integer productEstimate;

}

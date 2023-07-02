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
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "预计上市时间")
    private Date timeEstimate;

    @ApiModelProperty("预计时尚产量")
    private Integer marketEstimate;
}

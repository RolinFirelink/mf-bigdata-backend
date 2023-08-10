package com.arg.smart.web.product.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

/**
 * @description: 肉鸽价格表
 * @author cgli
 * @date: 2023-07-31
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("肉鸽价格表请求参数")
public class ReqRougePrice {

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "起止时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;

    @ApiModelProperty(value = "地区")
    private String region;

    @ApiModelProperty(value = "日龄")
    private String day;
}

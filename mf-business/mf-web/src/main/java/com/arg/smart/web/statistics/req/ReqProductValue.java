package com.arg.smart.web.statistics.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 城市产量数据
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("城市产量数据请求参数")
public class ReqProductValue {

    @ApiModelProperty("产品类型")
    private Integer flag;

    @ApiModelProperty("年份")
    private Integer year;
}

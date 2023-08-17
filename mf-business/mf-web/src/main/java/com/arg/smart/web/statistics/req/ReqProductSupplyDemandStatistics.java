package com.arg.smart.web.statistics.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 产品供需统计表
 * @author cgli
 * @date: 2023-07-17
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("产品供需统计表请求参数")
public class ReqProductSupplyDemandStatistics {

    @ApiModelProperty("产品类型")
    private Integer flag;

    @ApiModelProperty("开始日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @ApiModelProperty("结束日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;

}

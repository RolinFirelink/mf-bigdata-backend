package com.arg.smart.web.data.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;

/**
 * @description: 产品基地每日数据
 * @author cgli
 * @date: 2023-07-18
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("产品基地每日数据请求参数")
public class ReqProductBaseDayData {

    @ApiModelProperty(value="产品类型")
    private Integer flag;

    @ApiModelProperty(value = "开始日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty(value = "结束日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;


}

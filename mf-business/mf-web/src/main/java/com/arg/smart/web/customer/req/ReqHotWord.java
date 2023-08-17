package com.arg.smart.web.customer.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 热词
 * @author zsj
 * @date: 2023-07-15
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("热词表")
public class ReqHotWord {

    @ApiModelProperty("查询的个数")
    private Integer count;

    @ApiModelProperty(value = "热词")
    private String name;

    @ApiModelProperty(value = "情绪（-1:负面，0：中性，1：正面）")
    private Integer sentiment;

    @ApiModelProperty(value = "产品类型")
    private Integer flag;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "统计时间")
    private Date startTime;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "统计时间")
    private Date endTime;
}

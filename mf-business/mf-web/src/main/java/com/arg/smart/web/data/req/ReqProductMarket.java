package com.arg.smart.web.data.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 批发市场表
 * @author cgli
 * @date: 2023-08-18
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("批发市场表请求参数")
public class ReqProductMarket {
    @ApiModelProperty(value = "批发市场")
    private String market;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "记录日期")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "记录日期")
    private Date endTime;
    @ApiModelProperty(value = "区分字段")
    private Integer flag;

}

package com.arg.smart.web.docking.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 惠农网批发市场数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("惠农网批发市场数据请求参数")
public class ReqMarketPriceData {
    @ApiModelProperty("产品类别")
    private Integer flag;

    @ApiModelProperty("起始日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startDate;

    @ApiModelProperty("终止日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
}

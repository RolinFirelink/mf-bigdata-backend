package com.arg.smart.web.product.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 批发市场价格
 * @author cgli
 * @date: 2023-07-09
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("批发市场价格请求参数")
public class ReqMarketPrice {


    @ApiModelProperty(value = "区分字段")
    private Integer flag;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "产品地区")
    private String region;

    @ApiModelProperty(value = "起止时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty(value = "数据时间类型（1:月/2:季/3:半年/4:年）")
    private Integer timeType;

    @ApiModelProperty(value = "只查询最新日期")
    private Integer newDay;

    @ApiModelProperty(value = "查询数量")
    private Integer count;

}

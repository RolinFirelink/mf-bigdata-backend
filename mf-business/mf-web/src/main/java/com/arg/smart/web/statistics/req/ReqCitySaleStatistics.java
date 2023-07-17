package com.arg.smart.web.statistics.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 城市销售量表
 * @author cgli
 * @date: 2023-07-17
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("城市销售量表请求参数")
public class ReqCitySaleStatistics {

    @ApiModelProperty("产品类型")
    private Integer flag;

    @ApiModelProperty("返回的条数")
    private Integer count;
}

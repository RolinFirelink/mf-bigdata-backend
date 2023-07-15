package com.arg.smart.web.product.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 生产统计
 * @author cgli
 * @date: 2023-07-15
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("生产统计请求参数")
public class ReqProductionStatistics {

    @ApiModelProperty("查月/查年")
    private Integer searchType;

    @ApiModelProperty("返回的个数")
    private Integer count;

    @ApiModelProperty(value = "产品类型",required = true)
    private Integer flag;


}

package com.arg.smart.web.product.req;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 省份销售数据
 * @author cgli
 * @date: 2023-07-15
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("省份销售数据请求参数")
public class ReqProvinceSaleStatistics {

    @ApiModelProperty(value = "产品类型",required = true)
    private Integer flag;

    @ApiModelProperty(value = "数量")
    private Integer count;

    @ApiModelProperty(value = "查询类型（0：按销售量查，1：按价格查）")
    private Integer searchType;
}

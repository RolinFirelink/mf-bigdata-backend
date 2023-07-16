package com.arg.smart.web.statistics.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 采购商指数
 * @author cgli
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("采购商指数请求参数")
public class ReqBuyersIndex {

    @ApiModelProperty("产品类型")
    private Integer flag;

}

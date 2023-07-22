package com.arg.smart.web.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 销售流向
 * @author cgli
 * @date: 2023-07-18
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("销售流向请求参数")
public class ReqSalesFlow {

    @ApiModelProperty("产品类型")
    private Integer flag;

}

package com.arg.smart.web.average.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 均价表
 * @author cgli
 * @date: 2023-06-06
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("均价表请求参数")
public class ReqAveragePrice {

    @ApiModelProperty(value = "均价产品类型")
    private Integer flag;

    @ApiModelProperty(value = "均价地区")
    private String place;
}

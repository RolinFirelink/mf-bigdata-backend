package com.arg.smart.web.customer.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;
import lombok.Data;
import lombok.experimental.Accessors;

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
}

package com.arg.smart.web.cargo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 货运进度表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("货运进度表请求参数")
public class ReqFreightProgress {
    @ApiModelProperty(value = "运输状态")
    private String transportState;
}

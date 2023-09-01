package com.arg.smart.web.miniProgram.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 轮播图信息
 * @author cgli
 * @date: 2023-06-02
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("轮播图信息请求参数")
public class ReqRotationChart {
    @ApiModelProperty("状态")
    private Integer status;

    @ApiModelProperty("位置")
    private Integer position;
}

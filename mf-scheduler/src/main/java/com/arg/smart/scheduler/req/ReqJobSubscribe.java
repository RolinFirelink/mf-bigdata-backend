package com.arg.smart.scheduler.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 任务订阅表
 * @author cgli
 * @date: 2023-02-20
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("任务订阅表请求参数")
public class ReqJobSubscribe {
    @ApiModelProperty(value = "任务ID")
    private String jobId;
}

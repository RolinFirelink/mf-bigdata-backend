package com.arg.smart.scheduler.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 任务日志
 * @author cgli
 * @date: 2023-02-14
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("任务日志请求参数")
public class ReqJobLog extends ReqJob {
    @ApiModelProperty(value = "cron表达式")
    private String cron;
}

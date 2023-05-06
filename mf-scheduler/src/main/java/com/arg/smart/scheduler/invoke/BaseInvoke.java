package com.arg.smart.scheduler.invoke;

import com.arg.smart.common.scheduler.api.entity.JobLog;

import java.util.List;

/**
 * @description: 基础任务
 * @author cgli
 * @date: 2023/2/13 17:41
 */
public interface BaseInvoke {
    <T> Object run(JobLog jobLog, List<T> params);
}

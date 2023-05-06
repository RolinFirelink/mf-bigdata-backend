package com.arg.smart.scheduler.execute;

import com.arg.smart.common.scheduler.api.entity.JobLog;
import com.arg.smart.scheduler.invoke.BaseInvoke;

import java.util.List;

/**
 * @description: 通用job执行
 * @author cgli
 * @date: 2023/2/7 11:01
 */
public class GeneralJobExecute extends AbstractJobExecute {
    @Override
    protected <T> void execute(BaseInvoke baseJob, JobLog jobLog, List<T> params) {
        baseJob.run(jobLog, params);
    }
}

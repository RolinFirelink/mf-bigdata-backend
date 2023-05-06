package com.arg.smart.scheduler.service;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.scheduler.entity.Job;
import com.baomidou.mybatisplus.extension.service.IService;
import org.quartz.SchedulerException;

/**
 * @description: 定时调度任务
 * @author cgli
 * @date: 2023-02-03
 * @version: V1.0.0
 */
public interface JobService extends IService<Job> {
    Result<Job> insertJob(Job job) throws SchedulerException, ClassNotFoundException;
    Result<Job> updateJob(Job job) throws SchedulerException, ClassNotFoundException;
    Result<Boolean> deleteJob(String jobId) throws SchedulerException;
    Result<Boolean> executeJob(Job job);
    Result<Boolean> setStatus(Job job) throws SchedulerException, ClassNotFoundException;
}

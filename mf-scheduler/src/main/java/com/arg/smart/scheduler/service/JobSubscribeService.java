package com.arg.smart.scheduler.service;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.scheduler.entity.JobSubscribe;
import com.baomidou.mybatisplus.extension.service.IService;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * @description: 任务订阅表
 * @author cgli
 * @date: 2023-02-20
 * @version: V1.0.0
 */
public interface JobSubscribeService extends IService<JobSubscribe> {

    List<JobSubscribe> getSubscribesByJobId(String jobId);

    List<JobSubscribe> getSubscribesByJobIds(List<String> jobIds);

    int removeSubscribesByJobId(String jobId);

    Result<JobSubscribe> insertJobSubscribe(JobSubscribe jobSubscribe);

    Result<List<JobSubscribe>> insertJobSubscribes(List<JobSubscribe> jobSubscribeList);

    Result<Boolean> setStatus(JobSubscribe jobSubscribe) throws SchedulerException;
}

package com.arg.smart.scheduler.common;

import com.arg.smart.scheduler.entity.Job;
import com.arg.smart.scheduler.entity.JobMeta;
import com.arg.smart.scheduler.entity.JobSubscribe;
import com.arg.smart.scheduler.entity.TriggerMeta;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

/**
 * @description: 调度策略通用类
 * @author cgli
 * @date: 2023/2/6 21:11
 */
@Slf4j
public class SchedulerUtils {

    /**
     * 创建调度任务，同时创建触发器和任务
     *
     * @param scheduler
     * @param job
     */
    public static void createScheduler(Scheduler scheduler, Job job, JobSubscribe jobSubscribe, boolean cover) throws SchedulerException, ClassNotFoundException {
        createScheduler(scheduler, job, Arrays.asList(jobSubscribe), cover);
    }

    /**
     * 批量创建调度策略
     *
     * @param scheduler
     * @param job
     * @param jobSubscribeList
     * @param cover
     * @throws SchedulerException
     * @throws ClassNotFoundException
     */
    public static void createScheduler(Scheduler scheduler, Job job, List<JobSubscribe> jobSubscribeList, boolean cover) throws SchedulerException, ClassNotFoundException {
        JobMeta jobMeta = createJob(scheduler, job, cover);
        if (jobSubscribeList == null || jobSubscribeList.isEmpty()) {
            log.warn(MessageFormat.format("警告:未创建订阅策略!任务:{0}", job.getId()));
            return;
        }
        for (JobSubscribe subscribe : jobSubscribeList) {
            TriggerMeta triggerMeta = TriggerUtils.buildTriggerMeta(job, subscribe);
            TriggerUtils.createTrigger(scheduler, triggerMeta, jobMeta, cover);
            //任务状态或者策略状态为1时 暂停
            if (job.getStatus() == 1 || subscribe.getStatus() == 1) {
                TriggerUtils.pause(scheduler, triggerMeta);
            }
        }
    }

    /**
     * 创建任务
     *
     * @param scheduler
     * @param job
     * @param cover
     * @return
     * @throws SchedulerException
     * @throws ClassNotFoundException
     */
    public static JobMeta createJob(Scheduler scheduler, Job job, boolean cover) throws SchedulerException, ClassNotFoundException {
        JobMeta jobMeta = JobUtils.buildJobDetailMeta(job);
        JobUtils.createJob(scheduler, jobMeta, cover);
        return jobMeta;
    }


    /**
     * 批量删除触发策略
     *
     * @param scheduler
     * @param job
     * @param jobSubscribeList
     * @throws SchedulerException
     */
    public static void removeTrigger(Scheduler scheduler, Job job, List<JobSubscribe> jobSubscribeList) throws SchedulerException {
        for (JobSubscribe subscribe : jobSubscribeList) {
            removeTrigger(scheduler, job, subscribe);
        }
    }

    /**
     * 删除触发策略
     *
     * @param scheduler
     * @param job
     * @param jobSubscribe
     * @throws SchedulerException
     */
    public static void removeTrigger(Scheduler scheduler, Job job, JobSubscribe jobSubscribe) throws SchedulerException {
        TriggerUtils.remove(scheduler, TriggerUtils.buildTriggerMeta(job, jobSubscribe));
    }

    /**
     * 恢复任务
     *
     * @param scheduler
     * @param job
     * @param jobSubscribe
     * @return
     * @throws SchedulerException
     */
    public static void resume(Scheduler scheduler, Job job, JobSubscribe jobSubscribe) throws SchedulerException {
        //如果状态为停用不恢复
        if (jobSubscribe.getStatus() == 1 || job.getStatus() == 1) {
            return;
        }
        TriggerUtils.resume(scheduler, TriggerUtils.buildTriggerMeta(job, jobSubscribe));
    }

    /**
     * 批量恢复任务
     *
     * @param scheduler
     * @param job
     * @param jobSubscribeList
     * @throws SchedulerException
     */
    public static void resume(Scheduler scheduler, Job job, List<JobSubscribe> jobSubscribeList) throws SchedulerException {
        for (JobSubscribe subscribe : jobSubscribeList) {
            resume(scheduler, job, subscribe);
        }
    }

    /**
     * 批量暂停任务
     *
     * @param scheduler
     * @param job
     * @param jobSubscribeList
     * @throws SchedulerException
     */
    public static void pause(Scheduler scheduler, Job job, List<JobSubscribe> jobSubscribeList) throws SchedulerException {
        for (JobSubscribe subscribe : jobSubscribeList) {
            pause(scheduler, job, subscribe);
        }
    }

    /**
     * 暂停任务
     *
     * @param scheduler
     * @param job
     * @param jobSubscribe
     * @throws SchedulerException
     */
    public static void pause(Scheduler scheduler, Job job, JobSubscribe jobSubscribe) throws SchedulerException {
        TriggerUtils.pause(scheduler, TriggerUtils.buildTriggerMeta(job, jobSubscribe));
    }
}

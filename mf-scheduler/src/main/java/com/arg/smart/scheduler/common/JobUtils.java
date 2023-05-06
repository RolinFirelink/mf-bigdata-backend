package com.arg.smart.scheduler.common;


import com.arg.smart.scheduler.execute.DisallowConcurrentJobExecute;
import com.arg.smart.scheduler.execute.GeneralJobExecute;
import com.arg.smart.scheduler.entity.Job;
import com.arg.smart.scheduler.entity.JobMeta;
import com.alibaba.fastjson.JSON;
import org.quartz.*;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @description: 任务公共类
 * @author cgli
 * @date: 2023/2/6 15:43
 */
public class JobUtils {
    //job参数标签
    public static final String JOB_DATA_MAP = "job_data_map";

    /**
     * 获取任务key
     *
     * @param jobMeta
     * @return
     */
    public static JobKey getJobKey(JobMeta jobMeta) {
        return JobKey.jobKey(jobMeta.getName(), jobMeta.getGroup());
    }

    /**
     * 创建任务
     *
     * @param scheduler
     * @param jobMeta
     * @param cover
     * @throws SchedulerException
     * @throws ClassNotFoundException
     */
    public static void createJob(Scheduler scheduler, JobMeta jobMeta, boolean cover) throws SchedulerException, ClassNotFoundException {
        // 设置jobKey
        JobKey jobKey = new JobKey(jobMeta.getName(), jobMeta.getGroup());
        //判断job是否存在
        if (scheduler.checkExists(jobKey) && !cover) {
            throw new SchedulerException("job key : " + jobKey + "已存在!");
        }
        // 获得jobDataMap
        JobDataMap jobDataMap = buildJobDataMap(jobMeta);
        //实例化作业
        JobDetail jobDetail = JobBuilder.newJob(JobUtils.getJobClass(jobMeta.isAllowConcurrent()))
                .withIdentity(jobKey).withDescription(jobMeta.getDescription())
                .requestRecovery(jobMeta.getRecovery())
                .storeDurably(jobMeta.getDurability()).setJobData(jobDataMap).build();
        // 添加job
        scheduler.addJob(jobDetail, true, !jobMeta.getDurability());
    }

    public static JobDataMap buildJobDataMap(JobMeta jobMeta) {
        // 获得jobDataMap
        JobDataMap jobDataMap = new JobDataMap();
        if (jobMeta.getDataMap() != null && !jobMeta.getDataMap().isEmpty()) {
            jobDataMap.putAll(jobMeta.getDataMap());
        }
        return jobDataMap;
    }

    /**
     * 获取任务执行类
     *
     * @param allowConcurrent
     * @return
     */
    public static Class<? extends QuartzJobBean> getJobClass(boolean allowConcurrent) {
        return allowConcurrent ? GeneralJobExecute.class : DisallowConcurrentJobExecute.class;
    }

    /**
     * 构建任务元数据
     *
     * @param job
     * @return
     */
    public static JobMeta buildJobDetailMeta(Job job) {
        JobMeta jobDetailMeta = new JobMeta();
        jobDetailMeta.setName(MessageFormat.format("{0}:[{1}]", job.getJobName(), job.getId()));
        jobDetailMeta.setGroup(job.getJobGroup());
        jobDetailMeta.setDescription(job.getRemark());
        jobDetailMeta.setAllowConcurrent(1 == job.getAllowConcurrent());
        Map<String, Object> map = new HashMap<>();
        map.put(JOB_DATA_MAP, JSON.toJSONString(job));
        jobDetailMeta.setDataMap(map);
        return jobDetailMeta;
    }
}

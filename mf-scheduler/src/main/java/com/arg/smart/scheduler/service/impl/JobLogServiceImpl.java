package com.arg.smart.scheduler.service.impl;

import com.arg.smart.common.scheduler.api.entity.JobLog;
import com.arg.smart.scheduler.mapper.JobLogMapper;
import com.arg.smart.scheduler.service.JobLogService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @description: 任务日志
 * @author cgli
 * @date: 2023-02-14
 * @version: V1.0.0
 */
@Service
public class JobLogServiceImpl extends ServiceImpl<JobLogMapper, JobLog> implements JobLogService {

}

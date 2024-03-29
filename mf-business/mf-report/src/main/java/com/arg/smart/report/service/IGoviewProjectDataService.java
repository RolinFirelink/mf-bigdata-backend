package com.arg.smart.report.service;

import com.arg.smart.report.entity.GoviewProjectData;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fc
 * @since 2023-04-30
 */
public interface IGoviewProjectDataService extends IService<GoviewProjectData> {
	
	public GoviewProjectData getProjectid(String projectId);

}

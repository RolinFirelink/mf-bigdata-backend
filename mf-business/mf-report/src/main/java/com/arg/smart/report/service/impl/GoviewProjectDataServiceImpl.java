package com.arg.smart.report.service.impl;

import com.arg.smart.report.entity.GoviewProjectData;
import com.arg.smart.report.mapper.GoviewProjectDataMapper;
import com.arg.smart.report.service.IGoviewProjectDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author fc
 * @since 2023-04-30
 */
@Service
public class GoviewProjectDataServiceImpl extends ServiceImpl<GoviewProjectDataMapper, GoviewProjectData> implements IGoviewProjectDataService {
	@Autowired
	GoviewProjectDataMapper dataMapper;
	@Override
	public GoviewProjectData getProjectid(String projectId) {
		LambdaQueryWrapper<GoviewProjectData> lambdaQueryWrapper=new LambdaQueryWrapper<GoviewProjectData>();
		lambdaQueryWrapper.eq(GoviewProjectData::getProjectId, projectId);
		return dataMapper.selectOne(lambdaQueryWrapper);
		
	}

}

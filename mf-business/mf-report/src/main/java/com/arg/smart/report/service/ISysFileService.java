package com.arg.smart.report.service;

import com.arg.smart.report.entity.SysFile;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author fc
 * @since 2022-12-22
 */
public interface ISysFileService extends IService<SysFile> {

	
	public SysFile selectByExamplefileName(String filename);
}

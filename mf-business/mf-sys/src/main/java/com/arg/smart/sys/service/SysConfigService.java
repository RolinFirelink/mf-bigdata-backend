package com.arg.smart.sys.service;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.sys.entity.SysConfig;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 界面配置
 * @author cgli
 * @date: 2023-03-07
 * @version: V1.0.0
 */
public interface SysConfigService extends IService<SysConfig> {
    SysConfig querySysConfig(String userId);

    Result<SysConfig> saveSysConfig(SysConfig sysConfig);
}

package com.arg.smart.sys.service.impl;

import com.arg.smart.common.core.utils.AuthInfoUtils;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.sys.entity.SysConfig;
import com.arg.smart.sys.mapper.SysConfigMapper;
import com.arg.smart.sys.service.SysConfigService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @description: 界面配置
 * @author cgli
 * @date: 2023-03-07
 * @version: V1.0.0
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    @Override
    public SysConfig querySysConfig(String userId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<SysConfig>().eq(SysConfig::getUserId, userId));
    }

    @Override
    public Result<SysConfig> saveSysConfig(SysConfig sysConfig) {
        String userId = AuthInfoUtils.getCurrentUserId();
        SysConfig config = querySysConfig(userId);
        if (config != null) {
            config.setConfig(sysConfig.getConfig());
            if (baseMapper.updateById(config) == 1) {
                return Result.ok(sysConfig, "界面配置-编辑成功!");
            }
            return Result.fail(sysConfig, "错误:界面配置-编辑失败!");
        }
        sysConfig.setUserId(userId);
        if (baseMapper.insert(sysConfig) == 1) {
            return Result.ok(sysConfig, "界面配置-新增成功!");
        }
        return Result.fail(sysConfig, "错误:界面配置-新增失败!");
    }
}

package com.arg.smart.sys.service;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.sys.api.entity.DbConnect;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 数据库连接
 * @author cgli
 * @date: 2023-03-13
 * @version: V1.0.0
 */
public interface DbConnectService extends IService<DbConnect> {
    Result<DbConnect> insertConnect(DbConnect dbConnect);
    Result<DbConnect> updateConnect(DbConnect dbConnect);
    Result<Boolean> testConnect(DbConnect dbConnect);
    Result<DbConnect> queryById(String id);
}

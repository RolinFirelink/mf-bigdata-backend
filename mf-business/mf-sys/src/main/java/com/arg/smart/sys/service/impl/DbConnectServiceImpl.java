package com.arg.smart.sys.service.impl;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.common.dblink.entity.DataSourceOptions;
import com.arg.smart.common.dblink.enums.PoolType;
import com.arg.smart.common.dblink.manger.PoolManager;
import com.arg.smart.common.dblink.query.QueryHandler;
import com.arg.smart.sys.api.entity.DbConnect;
import com.arg.smart.sys.mapper.DbConnectMapper;
import com.arg.smart.sys.service.DbConnectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @description: 数据库连接
 * @author cgli
 * @date: 2023-03-13
 * @version: V1.0.0
 */
@Service
@RefreshScope
public class DbConnectServiceImpl extends ServiceImpl<DbConnectMapper, DbConnect> implements DbConnectService {
    @Value(value = "${DBConnect.password.privateKey}")
    private String privateKey;

    @Override
    public Result<DbConnect> insertConnect(DbConnect dbConnect) {
        Result result = testConnect(dbConnect);
        if (!result.isSuccess()) {
            return Result.fail(dbConnect, "错误:尝试连接失败!");
        }
        if (save(dbConnect)) {
            return Result.ok(dbConnect, "数据库连接-添加成功!");
        }
        return Result.fail(dbConnect, "错误:数据库连接-添加失败!");
    }

    @Override
    public Result<DbConnect> updateConnect(DbConnect dbConnect) {
        Result result = testConnect(dbConnect);
        if (!result.isSuccess()) {
            return Result.fail(dbConnect, "错误:尝试连接失败!");
        }
        if (updateById(dbConnect)) {
            return Result.ok(dbConnect, "数据库连接-编辑成功!");
        }
        return Result.fail(dbConnect, "错误:数据库连接-编辑失败!");
    }

    @Override
    public Result<Boolean> testConnect(DbConnect dbConnect) {
        DataSourceOptions dataSourceOptions = QueryHandler.buildDataSourceOptions(dbConnect, privateKey);
        //测试连接不使用连接池
        dataSourceOptions.setPoolType(PoolType.NoPool);
        try {
            Connection conn = PoolManager.getConnection(dataSourceOptions, 3000);
            return Result.ok(!conn.isClosed(), "连接成功");
        } catch (SQLException e) {
            log.error("错误:测试连接异常", e);
            return Result.fail(false, "错误:连接失败");
        } finally {
            PoolManager.release();
        }
    }

    /**
     * 通过ID查询数据库配置
     *
     * @param id
     * @return
     */
    public Result<DbConnect> queryById(String id) {
        DbConnect dbConnect = baseMapper.selectById(id);
        if (dbConnect == null) {
            return Result.fail(null, "错误:未获取到数据连接");
        }
        return Result.ok(dbConnect, "数据库连接-查询成功!");
    }
}

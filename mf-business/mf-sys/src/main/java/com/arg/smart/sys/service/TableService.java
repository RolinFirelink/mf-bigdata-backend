package com.arg.smart.sys.service;

import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.dblink.datatable.MetaDataTable;
import com.arg.smart.sys.api.entity.FieldInfo;
import com.arg.smart.sys.api.entity.TableInfo;

import java.util.List;

/**
 * @author cgli
 * @description: 表信息
 * @date: 2022/8/31 22:59
 */
public interface TableService {
    /**
     * 获取表字段信息
     *
     * @param connectId 数据库连接ID
     * @param tableName 表名
     * @return
     */
    List<FieldInfo> getFieldList(String connectId, String tableName, ReqPage reqPage);

    /**
     * 获取表信息
     *
     * @param connectId 数据库连接ID
     * @param tableName 表名
     * @return
     */
    TableInfo getTableInfo(String connectId, String tableName, ReqPage reqPage);

    /**
     * 获取表列表
     *
     * @param connectId 数据库连接ID
     * @param tableName 表名
     * @return
     */
    List<TableInfo> getTableList(String connectId, String tableName, ReqPage reqPage);

    /**
     * 获取数据
     *
     * @param connectId 数据库连接ID
     * @param tableName 表名
     * @return
     */
    MetaDataTable getDataTable(String connectId, String tableName, ReqPage reqPage);
}

package com.arg.smart.common.dblink;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.dblink.datatable.MetaDataRow;
import com.arg.smart.common.dblink.datatable.MetaDataTable;
import com.arg.smart.common.dblink.entity.DataSourceOptions;
import com.arg.smart.common.dblink.enums.DBType;
import com.arg.smart.common.dblink.enums.PoolType;
import com.arg.smart.common.dblink.page.MfPageHelper;
import com.arg.smart.common.dblink.query.QueryHandler;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 测试查询
 * @author cgli
 * @date: 2023/3/21
 */
public class TestQuery {
    @Test
    public void query() {
        DataSourceOptions options = new DataSourceOptions();
        options.setUser("root");
        options.setPassword("3368");
        options.setJdbcUrl("jdbc:mysql://localhost:3306/mf_oauth?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=true&serverTimezone=GMT%2B8");
        options.setDbType(DBType.mysql);
        options.setPoolType(PoolType.Hikari);
        MetaDataTable table = QueryHandler.query(options, "select * from sso_menu");
        System.out.println(JSON.toJSONString(table.getColHeaders()));
        System.out.println(JSON.toJSONString(table));
    }

    @Test
    public void pageQuery() {
        DataSourceOptions options = new DataSourceOptions();
        options.setUser("root");
        options.setPassword("123456");
        options.setJdbcUrl("jdbc:mysql://localhost:3306/mf_oauth?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8");
        options.setDbType(DBType.mysql);
        options.setPoolType(PoolType.Hikari);
        MfPageHelper.startPage(1, 10);
        PageResult<MetaDataRow> table = new PageResult<>(QueryHandler.query(options, "select * from sso_menu"));
        System.out.println(JSON.toJSONString(table));
    }

    @Test
    public void pageQueryParams() {
        DataSourceOptions options = new DataSourceOptions();
        options.setUser("root");
        options.setPassword("123456");
        options.setJdbcUrl("jdbc:mysql://localhost:3306/mf_oauth?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8");
        options.setDbType(DBType.mysql);
        options.setPoolType(PoolType.Hikari);
        MfPageHelper.startPage(1, 10);
        List<Object> params = new ArrayList<>();
        params.add("聊天");
        PageResult<MetaDataRow> table = new PageResult<>(QueryHandler.query(options, "select * from sso_menu where menu_name=?", params));
        System.out.println(JSON.toJSONString(table));
    }

}

package com.arg.smart.common.dblink.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: sql包装类
 * @author cgli
 * @date: 2023/3/21 23:01
 */
@Data
@Accessors(chain = true)
@ApiModel("sql包装")
public class BoundSql {
    @ApiModelProperty("查询SQL")
    private String sql;
    @ApiModelProperty("查询参数")
    private List<Object> params;

    public BoundSql() {
        this("");
    }

    /**
     * SQL包装类构造函数
     *
     * @param sql sql语句
     */
    public BoundSql(String sql) {
        this(sql, null);
    }

    /**
     * SQL包装类构造函数
     *
     * @param sql    sql语句
     * @param params 参数
     */
    public BoundSql(String sql, List<Object> params) {
        this.sql = sql;
        if (params == null) {
            params = new ArrayList<>();
        }
        this.params = params;
    }
}

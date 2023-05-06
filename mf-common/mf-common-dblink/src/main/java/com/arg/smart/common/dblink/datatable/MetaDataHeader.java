package com.arg.smart.common.dblink.datatable;

import com.arg.smart.common.core.enums.DataType;
import com.arg.smart.common.dblink.enums.TargetType;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;
import java.util.Objects;

/**
 * @description: 元数据列头
 * @author cgli
 * @date: 2023/3/20
 */
@Data
@Accessors(chain = true)
public class MetaDataHeader implements Serializable {
    public MetaDataHeader() {

    }

    public MetaDataHeader(String colName, String fieldName, String expression, DataType dataType, TargetType targetType) {
        this.colName = colName;
        this.fieldName = fieldName;
        this.expression = expression;
        this.dataType = dataType;
        this.targetType = targetType;
    }

    /**
     * 列名(必须唯一)
     * 通过字段自动生成列名或用户更改的别名
     */
    private String colName;
    /**
     * 字段名称 对应数据库中字段
     */
    private String fieldName;

    /**
     * 运算式子 运算指标设置该属性 主要用于显示
     */
    private String expression;

    /**
     * 数据类型
     */
    private DataType dataType;

    /**
     * 字段所属 表别名 原生查询不赋值
     */
    private String tableAlias;

    /**
     * 指标类型
     */
    private TargetType targetType;

    public MetaDataHeader setDataType(String dataType) {
        this.dataType = DataType.forType(dataType);
        return this;
    }

    public MetaDataHeader setDataType(DataType dataType) {
        this.dataType = dataType;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        MetaDataHeader that = (MetaDataHeader) o;
        return Objects.equals(colName, that.colName)
                && Objects.equals(fieldName, that.fieldName)
                && Objects.equals(expression, that.expression)
                && dataType == that.dataType
                && Objects.equals(tableAlias, that.tableAlias)
                && targetType == that.targetType;
    }

    /**
     * 拷贝一个头
     *
     * @return
     */
    public MetaDataHeader copy() {
        MetaDataHeader newHeader = new MetaDataHeader();
        BeanUtils.copyProperties(this, newHeader);
        return newHeader;
    }
}

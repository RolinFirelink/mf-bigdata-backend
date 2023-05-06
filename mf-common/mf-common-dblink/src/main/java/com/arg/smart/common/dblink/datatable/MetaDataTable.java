package com.arg.smart.common.dblink.datatable;

import com.arg.smart.common.core.exception.MyRuntimeException;
import com.arg.smart.common.dblink.common.Constant;
import com.github.pagehelper.Page;

import java.util.Collection;
import java.util.List;

/**
 * @description: 元数据表格
 * @author cgli
 * @date: 2023/3/20
 */
public class MetaDataTable extends Page<MetaDataRow> {
    private MetaDataHeaders headers = new MetaDataHeaders();

    public MetaDataTable() {
        super();
    }

    public MetaDataTable(MetaDataHeaders headers) {
        this();
        addColumn(headers);
    }

    public MetaDataTable(MetaDataHeaders headers, int pageNum, int pageSize) {
        super(pageNum, pageSize);
        addColumn(headers);
    }

    public MetaDataTable(int pageNum, int pageSize) {
        super(pageNum, pageSize);
    }

    /**
     * 新增行
     *
     * @param row 行数据
     */
    @Override
    public boolean add(MetaDataRow row) {
        checkHeader(row.getColHeaders());
        return super.add(row);
    }

    /**
     * 新增行
     *
     * @param index 行索引
     * @param row   行数据
     */
    @Override
    public void add(int index, MetaDataRow row) {
        checkHeader(row.getColHeaders());
        super.add(index, row);
    }

    /**
     * 新增多行
     *
     * @param rows 行列表
     * @return
     */
    @Override
    public boolean addAll(Collection<? extends MetaDataRow> rows) {
        for (MetaDataRow row : rows) {
            checkHeader(row.getColHeaders());
        }
        return super.addAll(rows);
    }

    /**
     * 新增多行
     *
     * @param index 索引
     * @param rows  行列表
     * @return
     */
    @Override
    public boolean addAll(int index, Collection<? extends MetaDataRow> rows) {
        for (MetaDataRow row : rows) {
            checkHeader(row.getColHeaders());
        }
        return super.addAll(index, rows);
    }

    /**
     * 检查头列头是否相同
     *
     * @param metaDataHeaders 元数据头
     */
    private void checkHeader(MetaDataHeaders metaDataHeaders) {
        if (!metaDataHeaders.equals(this.headers)) {
            throw new MyRuntimeException(Constant.ErrorRowException);
        }
    }

    /**
     * 增加列
     *
     * @param columns
     */
    public void addColumn(MetaDataHeaders columns) {
        for (MetaDataHeader header : columns.values()) {
            addColumn(header);
        }
    }

    /**
     * 增加列
     *
     * @param col 列
     */
    public void addColumn(MetaDataHeader col) {
        this.headers.addColumn(col);
        for (MetaDataRow row : this) {
            row.addColumn(col, null);
        }
    }

    /**
     * 表格删除一列
     *
     * @param colName 列名
     * @return
     */
    public void removeColumn(String colName) {
        if (this.headers.containsKey(colName)) {
            this.headers.remove(colName);
            for (MetaDataRow row : this) {
                row.removeColumn(colName);
            }
        }
    }

    /**
     * 表格根据索引删除一列
     *
     * @param index 索引
     */
    public void removeColumn(int index) {
        MetaDataHeader col = headers.getColHeader(index);
        if (col != null) {
            removeColumn(col.getColName());
        }
    }

    /**
     * 合并表格
     *
     * @param table 新表格
     */
    public MetaDataTable mergeTable(MetaDataTable table) {
        if (this.size() != table.size()) {
            throw new MyRuntimeException("行数不相等不允许合并");
        }
        this.addColumn(table.getColHeaders());
        for (int i = 0; i < this.size(); i++) {
            for (int j = 0; j < table.getColHeaders().size(); j++) {
                this.get(i).addColumn(table.getColHeader(j), table.getCellValue(i, j));
            }
        }
        return this;
    }

    /**
     * 合并表格
     *
     * @param list 行列表
     */
    public MetaDataTable mergeTable(List<MetaDataRow> list) {
        if (this.size() != list.size()) {
            throw new MyRuntimeException("行数不相等不允许合并");
        }
        if (list.isEmpty()) {
            throw new MyRuntimeException(Constant.NotFoundException);
        }
        MetaDataTable table = new MetaDataTable(list.get(0).getColHeaders());
        table.addAll(list);
        return mergeTable(table);
    }

    /**
     * 获取所有列
     *
     * @return
     */
    public MetaDataHeaders getColHeaders() {
        return headers;
    }

    /**
     * 判断是否包含列
     *
     * @param colName 列名
     * @return
     */
    public boolean containColumn(String colName) {
        if (headers == null || headers.size() == 0) {
            return false;
        }
        return headers.containsKey(colName);
    }

    /**
     * 根据列索引获取列
     *
     * @param index
     * @return
     */
    public MetaDataHeader getColHeader(int index) {
        if (headers == null || headers.size() == 0) {
            return null;
        }
        return headers.getColHeader(index);
    }

    /**
     * 根据列名称获取
     *
     * @param alias 列别名
     * @return
     */
    public MetaDataHeader getColHeader(String alias) {
        if (containColumn(alias)) {
            return headers.getColHeader(alias);
        }
        return null;
    }

    /**
     * 根据行索引列别名获取当前值
     *
     * @param rowIndex 行索引
     * @param colName  列名称
     * @return
     */
    public Object getCellValue(int rowIndex, String colName) {
        MetaDataRow biMetaDataRow = this.get(rowIndex);
        if (biMetaDataRow == null) {
            return null;
        }
        return biMetaDataRow.getCellValue(colName);
    }

    /**
     * 根据行索引 列索引获取值
     *
     * @param rowIndex 行索引
     * @param colIndex 列索引
     * @return
     */
    public Object getCellValue(int rowIndex, int colIndex) {
        MetaDataRow biMetaDataRow = this.get(rowIndex);
        if (biMetaDataRow == null) {
            return null;
        }
        return biMetaDataRow.getCellValue(colIndex);
    }
}

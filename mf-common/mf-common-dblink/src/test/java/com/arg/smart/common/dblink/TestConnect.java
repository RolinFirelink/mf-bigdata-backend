package com.arg.smart.common.dblink;

import com.arg.smart.common.dblink.datatable.MetaDataHeader;
import com.arg.smart.common.dblink.datatable.MetaDataHeaders;
import com.arg.smart.common.dblink.datatable.MetaDataRow;
import com.arg.smart.common.dblink.datatable.MetaDataTable;
import com.arg.smart.common.dblink.dbpool.PoolWrapper;
import com.arg.smart.common.dblink.entity.DataSourceOptions;
import com.arg.smart.common.dblink.enums.DBType;
import com.arg.smart.common.core.enums.DataType;
import com.arg.smart.common.dblink.enums.PoolType;
import com.arg.smart.common.dblink.enums.TargetType;
import com.arg.smart.common.dblink.manger.PoolManager;
import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 测试连接池
 * @author cgli
 * @date: 2023/3/16 22:00
 */
public class TestConnect {
    @Test
    public void poolTest() {
        DataSourceOptions options = new DataSourceOptions();
        options.setUser("root");
        options.setPassword("3368");
        options.setJdbcUrl("jdbc:mysql://localhost:3306/mf_oauth?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&useSSL=false&serverTimezone=GMT%2B8");
        options.setDbType(DBType.mysql);
        for (int i = 0; i < 10; i++) {
            try {
                if (i / 2 == 0) {
                    options.setPoolType(PoolType.Hikari);
                } else {
                    options.setPoolType(PoolType.Druid);
                }
                Connection connection = PoolManager.getConnection(options, 5000);
                System.out.println(connection.isClosed());
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            } finally {
                PoolManager.release();
            }
        }
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Test
    public void poolCreate() {
        for (int i = 0; i < 5; i++) {
            PoolWrapper wrapper = PoolType.Druid.createPool();
            System.out.println(wrapper);
        }
    }

    @Test
    public void testDataTable() {
        MetaDataTable table = new MetaDataTable();
        for (int i = 0; i < 10; i++) {
            table.addColumn(new MetaDataHeader(i + "", i + "", i + "", DataType.STRING, TargetType.ORIGINAL));
        }
        List<MetaDataRow> list = new ArrayList<>();
        for (int j = 0; j < 5; j++) {
            MetaDataRow row = new MetaDataRow(table.getColHeaders());
            for (int i = 0; i < 10; i++) {
                row.setCellValue(table.getColHeader(i).getColName(), i);
            }
            list.add(row);
        }
        table.addAll(list);
        table.add(3, new MetaDataRow(table.getColHeaders()));
        table.addColumn(new MetaDataHeader("", "5", "", DataType.STRING, TargetType.ORIGINAL));
        table.removeColumn("5_1");
        table.removeColumn(7);
        MetaDataTable table2 = new MetaDataTable();
        List<MetaDataRow> list2 = new ArrayList<>();
        for (int j = 0; j < 6; j++) {
            MetaDataRow row = new MetaDataRow(table2.getColHeaders());
            for (int i = 1; i < 5; i++) {
                row.addColumn(new MetaDataHeader().setFieldName(i + ""), i);
            }
            table2.add(j, row);
            list2.add(row);
        }
//        MetaDataTable table3 = table.mergeTable(table2);
        MetaDataTable table4 = table.mergeTable(list2);
        System.out.println(JSON.toJSONString(table));
    }

    @Test
    public void testDataRow() {
        MetaDataHeaders headers = new MetaDataHeaders();
        for (int i = 0; i < 5; i++) {
            headers.addColumn(new MetaDataHeader(i + "", i + "", i + "", DataType.STRING, TargetType.ORIGINAL));
        }
        MetaDataRow row = new MetaDataRow(headers);
        row.setCellValue("0", 1);
        row.setCellValue("1", 1);
        row.setCellValue("2", 2);
        row.addColumn(new MetaDataHeader("1", "1", "", DataType.STRING, TargetType.ORIGINAL), 1);
        row.setCellValue(4, 5);
        row.removeCell("1");
        row.removeCell(0);
        Object value = row.getCellValue("2");
        value = row.getCellValue(5);
        row.setCellValue("1", 1);
        row.setCellValue("2", 2);
        value = row.getColHeader("2");
        value = row.getColHeader(3);
        MetaDataRow row1 = new MetaDataRow(headers);
        row1.setCellValue("1", 1);
        row1.setCellValue("2", 2);
        row1.setCellValue(3, 3);
        value = row.compareTo(row1);
        System.out.println(JSON.toJSONString(row));
    }

}

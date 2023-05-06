package com.arg.smart.code.test;

import com.arg.smart.code.common.FreemarkerUtils;
import com.arg.smart.code.entity.CodeInfo;
import com.arg.smart.sys.api.entity.FieldInfo;
import com.arg.smart.sys.api.entity.TableInfo;
import com.arg.smart.common.core.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cgli
 * @description: 代码生成测试
 * @date: 2022/8/24 16:02
 */
@Slf4j
@SpringBootTest
@ComponentScan(basePackages = "com.arg.smart.code")
public class CodeTest {
    @Resource
    FreemarkerUtils freemarkerUtils;

    @Test
    public void testBuildCode() {
        CodeInfo codeInfo = new CodeInfo();
        codeInfo.setPackageName("com.arg.smart.code");
        codeInfo.setEntityName(StringUtils.toCamelBigCase("test_code"));
        List<FieldInfo> list = new ArrayList<>();
        list.add(new FieldInfo().setFieldName("F1").setDbType("VARCHAR").setType("String").setComment("字段1").setIsPrimary(true));
        list.add(new FieldInfo().setFieldName("F2").setDbType("INT").setType("Integer").setComment("字段2"));
        list.add(new FieldInfo().setFieldName("F3").setDbType("INT").setType("Integer").setComment("字段3"));
        codeInfo.setTableInfo(new TableInfo().setTableName("test_table").setTableComment("测试").setColumns(list));
        String aaa = freemarkerUtils.buildCode("entity", codeInfo);
        System.out.println(aaa);
    }
    @Test
    public void testGetCode() {
        CodeInfo codeInfo = new CodeInfo();
        codeInfo.setPackageName("com.arg.smart.code");
        codeInfo.setEntityName(StringUtils.toCamelBigCase("test_code"));
        List<FieldInfo> list = new ArrayList<>();
        list.add(new FieldInfo().setFieldName("F1").setDbType("VARCHAR").setType("String").setComment("字段1").setIsPrimary(true));
        list.add(new FieldInfo().setFieldName("F2").setDbType("INT").setType("Integer").setComment("字段2"));
        list.add(new FieldInfo().setFieldName("F3").setDbType("INT").setType("Integer").setComment("字段3"));
        codeInfo.setTableInfo(new TableInfo().setTableName("test_table").setTableComment("测试").setColumns(list));
        freemarkerUtils.getCode(codeInfo);
    }

}

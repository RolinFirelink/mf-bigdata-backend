package com.arg.smart.web.customer.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustomerExcel{

    @ExcelProperty(value = "唯一ID",index = 0)
    private Long id;
    @ExcelProperty(value = "客户名称",index = 1)
	private String name;
    @ExcelProperty(value = "年龄",index = 2)
    private Integer age;
    @ExcelProperty(value = "性别",index = 3)
    private Integer gender;
    @ExcelProperty(value = "职业",index = 4)
    private String occupation;
    @ExcelProperty(value = "城市",index = 5)
    private String city;
    @ExcelProperty(value = "关联客户编号",index = 6)
	private Long platformUserId;
    @ExcelProperty(value = "第三方平台ID",index = 7)
	private Long platformId;
    @ExcelProperty(value = "产品分类",index = 8)
	private Integer flag;
    @ExcelProperty(value = "备注",index = 9)
	private String remark;
}

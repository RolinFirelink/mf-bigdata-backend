package com.arg.smart.web.company.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.ToString;

/**
 * @description: 产品基地
 * @author cgli
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Data
@ToString
public class ProductBaseExcel{
    @ExcelProperty(value = "id",index = 0)
    @TableId(type = IdType.AUTO)
    private String id;
    @ExcelProperty(value = "基地名称",index = 1)
	private String companyName;
    @ExcelProperty(value = "基地编码",index = 2)
	private String companyNo;
    @ExcelProperty(value = "联系人",index = 3)
	private String contacts;
    @ExcelProperty(value = "联系电话",index = 4)
	private String contactPhone;
    @ExcelProperty(value = "电子邮箱",index = 5)
	private String email;
    @ExcelProperty(value = "行政区域名称",index = 6)
	private String areaName;
    @ExcelProperty(value = "行政区域编码",index = 7)
	private String areaCode;
    @ExcelProperty(value = "基地详细地址",index = 8)
	private String address;
    @ExcelProperty(value = "基地产品类型",index = 9)
	private String productType;
    @ExcelProperty(value = "启用状态",index = 10)
	private String enabled;
    @ExcelProperty(value = "企业ID",index = 11)
	private String parentId;
    @ExcelProperty(value = "备注",index = 12)
	private String remark;
}

package com.arg.smart.web.company.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CompanyExcel {

    @ExcelProperty(value = "id",index = 0)
    @TableId(type = IdType.AUTO)
    private String id;
    @ExcelProperty(value = "公司名称",index = 1)
    private String companyName;
    @ExcelProperty(value = "公司编码",index = 2)
    private String companyNo;
    @ExcelProperty(value = "法人",index = 3)
    private String juridicalPerson;
    @ExcelProperty(value = "法人电话",index = 4)
    private String juridicalPhone;
    @ExcelProperty(value = "联系人",index = 5)
    private String contacts;
    @ExcelProperty(value = "联系电话",index = 6)
    private String contactPhone;
    @ExcelProperty(value = "电子邮箱",index = 7)
    private String email;
    @ExcelProperty(value = "行政区域名称",index = 8)
    private String areaName;
    @ExcelProperty(value = "行政区域编码",index = 9)
    private String areaCode;
    @ExcelProperty(value = "公司地址",index = 10)
    private String address;
    @ExcelProperty(value = "公司类型",index = 11)
    private String companyType;
    @ExcelProperty(value = "公司产品类型",index = 12)
    private String productType;
    @ExcelProperty(value = "是否启用",index = 13)
    private String enabled;
    @ExcelProperty(value = "备注",index = 14)
    private String remark;
    @ApiModelProperty(value = "0--未删除 1--已删除 DIC_NAME=DELETE_FLAG")
    private Integer deleteFlag;
    @ApiModelProperty(value = "自定义拓展字段JSON 结构(如果公司类型为承运商，要在该字段加上承运商商业性质标识（1=物流公司、2=运输公司、3=快递公司）)")
    private String extendField;
}

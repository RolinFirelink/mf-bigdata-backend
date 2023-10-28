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

    @ExcelProperty(index = 0)
    private Long id;
    @ExcelProperty(index = 1)
    private String nameOfClassification;
    @ExcelProperty(index = 8)
    private String companyName;
    @ExcelProperty(index = 2)
    private String contacts;
    @ExcelProperty(index = 3)
    private String contactPhone;
    @ExcelProperty(index = 4)
    private String province;
    @ExcelProperty(index = 5)
    private String city;
    @ExcelProperty(index = 6)
    private String region;
    @ExcelProperty(index = 7)
    private String address;
    @ExcelProperty(index = 11)
    private Integer companyType;
    @ExcelProperty(index = 12)
    private String remark;
    @ExcelProperty(index = 9)
    private String businessScope;
    @ExcelProperty(index = 10)
    private String affiliatedExhibitions;
}

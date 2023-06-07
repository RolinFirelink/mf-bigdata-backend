package com.arg.smart.web.cargo.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FreightProgressExcel {
    @ExcelProperty(value = "唯一ID", index = 0)
    private String id;
    @ExcelProperty(value = "运输状态", index = 1)
    private String transportState;
    @ExcelProperty(value = "进度地点,各个地点之间用,分隔", index = 2)
    private String transportLocation;
    @ExcelProperty(value = "货运编号", index = 3)
    private String freightNumber;
}

package com.arg.smart.web.order.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;

import java.util.Date;

@Data
@ToString
public class OrderExcel {

    @ExcelProperty(value = "ID", index = 0)
    private Long id;
    @ExcelProperty(value = "订单类型", index = 1)
    private Integer category;
    @ExcelProperty(value = "供应商企业ID", index = 2)
    private Long vendorId;
    @ExcelProperty(value = "供应企业名称", index = 3)
    private String vendorName;
    @ExcelProperty(value = "采购商ID", index = 4)
    private Long buyerId;
    @ExcelProperty(value = "采购商名称", index = 5)
    private String buyerName;
    @ExcelProperty(value = "订单状态", index = 6)
    private Integer status;
    @ExcelProperty(value = "订单创建时间", index = 7)
    private Date startTime;
    @ExcelProperty(value = "订单完成时间", index = 8)
    private Date finishTime;
    @ExcelProperty(value = "关联产品", index = 9)
    private Integer flag;
    @ExcelProperty(value = "备注", index = 10)
    private String remark;
}

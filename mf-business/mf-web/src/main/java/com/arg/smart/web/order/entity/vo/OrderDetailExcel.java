package com.arg.smart.web.order.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@ToString
public class OrderDetailExcel {

    @ExcelProperty(value = "唯一ID", index = 0)
    private Long id;
    @ExcelProperty(value = "订单ID", index = 1)
    private Long orderId;
    @ExcelProperty(value = "产品编号", index = 2)
    private Long materialId;
    @ExcelProperty(value = "产品名称", index = 3)
    private String materialName;
    @ExcelProperty(value = "销售数量", index = 4)
    private Long salesQuantity;
    @ExcelProperty(value = "销售单价", index = 5)
    private BigDecimal salesAmount;
    @ExcelProperty(value = "计量单位", index = 6)
    private String unit;
    @ExcelProperty(value = "产品区分字段", index = 7)
    private Integer flag;
}

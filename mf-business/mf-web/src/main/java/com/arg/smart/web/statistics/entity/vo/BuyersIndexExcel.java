package com.arg.smart.web.statistics.entity.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author cgli
 * @description: 采购商指数Excel
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Data
@ToString
public class BuyersIndexExcel{
    @ExcelProperty(value = "id", index = 0)
    @TableId(type = IdType.AUTO)
    private Long id;
    @ExcelProperty(value = "年份", index = 1)
    private Integer year;
    @ExcelProperty(value = "月份", index = 2)
    private Integer month;
    @ExcelProperty(value = "指数", index = 3)
    private BigDecimal temp;
    @ExcelProperty(value = "产品类型", index = 4)
    private Integer flag;
    @ExcelProperty(value = "统计时间", index = 5)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date statisticalTime;
}

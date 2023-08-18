package com.arg.smart.web.data.entity.vo;

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
 * @description: 产品基地每日数据Excel
 * @date: 2023-07-18
 * @version: V1.0.0
 */
@Data
@ToString
public class ProductMarketExcel {
    @ExcelProperty(value = "唯一ID", index = 0)
    @TableId(type = IdType.AUTO)
    private Long id;
    @ExcelProperty(value = "批发市场", index = 1)
    private String market;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = "记录日期", index = 2)
    private Date recordDate;
    @ExcelProperty(value = "产品类型", index = 3)
    private Integer flag;
    @ExcelProperty(value = "行政区域编码", index = 4)
    private String areaCode;
    @ExcelProperty(value = "纬度", index = 5)
    private BigDecimal lat;
    @ExcelProperty(value = "经度", index = 6)
    private BigDecimal lng;
}

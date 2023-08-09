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
 * @description: 基地产品生产规模数据表Excel
 * @author cgli
 * @date: 2023-07-20
 * @version: V1.0.0
 */
@Data
@ToString
public class BaseProductProductionScaleExcel{
    @ExcelProperty(value = "唯一ID",index = 0)
    @TableId(type = IdType.AUTO)
    private Long id;
    @ExcelProperty(value = "生产规模",index = 1)
	private BigDecimal productionScale;
    @ExcelProperty(value = "基地ID",index = 2)
	private Long baseId;
    @ExcelProperty(value = "规模单位",index = 3)
	private String scaleUnit;
    @ExcelProperty(value = "产品",index = 4)
	private String product;
    @ExcelProperty(value = "产品类型",index = 5)
	private Integer flag;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = "统计日期",index = 6)
	private Date statisticalTime;
}

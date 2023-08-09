package com.arg.smart.web.statistics.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 生产统计
 * @author cgli
 * @date: 2023-07-15
 * @version: V1.0.0
 */
@Data
@ToString
public class ProductionStatisticsExcel{
    @ExcelProperty(value = "唯一ID",index = 0)
    @TableId(type = IdType.AUTO)
    private Long id;
    @ExcelProperty(value = "生产规模",index = 1)
	private Integer produceScale;
    @ExcelProperty(value = "产量",index = 2)
	private String yield;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = "统计时间",index = 3)
	private Date statisticalTime;
    @ExcelProperty(value = "产量类型",index = 4)
	private Integer flag;
    @ExcelProperty(value = "月份",index = 5)
	private Integer month;
    @ExcelProperty(value = "年份",index = 6)
    private Integer year;
    @ExcelProperty(value = "产量单位",index = 7)
    private String yieldUnit;
}

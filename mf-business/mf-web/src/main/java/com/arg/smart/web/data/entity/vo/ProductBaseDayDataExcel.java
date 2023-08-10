package com.arg.smart.web.data.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 产品基地每日数据Excel
 * @author cgli
 * @date: 2023-07-18
 * @version: V1.0.0
 */
@Data
@ToString
public class ProductBaseDayDataExcel{
    @ExcelProperty(value = "唯一ID",index = 0)
    @TableId(type = IdType.AUTO)
    private Long id;
    @ExcelProperty(value = "生产基地id",index = 1)
    private Long baseId;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = "日期",index = 2)
	private Date time;
    @ExcelProperty(value = "产品类型",index = 3)
	private Integer flag;
    @ExcelProperty(value = "产品",index = 4)
	private String product;
    @ExcelProperty(value = "供应量",index = 5)
	private BigDecimal supply;
    @ExcelProperty(value = "产量",index = 6)
	private BigDecimal yield;
    @ExcelProperty(value = "产量单位",index = 7)
	private String yieldUnit;
    @ExcelProperty(value = "销售额",index = 8)
	private BigDecimal sales;
    @ExcelProperty(value = "销售额单位",index = 9)
	private String salesUnit;
    @ExcelProperty(value = "需求量",index = 10)
	private BigDecimal demand;
    @ExcelProperty(value = "供应和需求单位",index = 11)
	private String unit;
    @ExcelProperty(value = "销售量",index = 12)
	private BigDecimal salesVolume;
    @ExcelProperty(value = "销售量单位",index = 13)
	private String salesVolumeUnit;
}

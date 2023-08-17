package com.arg.smart.web.statistics.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
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

import java.util.Date;

/**
 * @description: 产品供需统计表
 * @author cgli
 * @date: 2023-07-17
 * @version: V1.0.0
 */
@Data
@ToString
public class ProductSupplyDemandStatisticsExcel {
    @ExcelProperty(value = "唯一ID",index = 0)
    @TableId(type = IdType.AUTO)
    private Long id;
    @ExcelProperty(value = "产品",index = 1)
	private String product;
    @ExcelProperty(value = "供应",index = 2)
	private Integer supply;
    @ExcelProperty(value = "需求",index = 3)
	private Integer demand;
    @ExcelProperty(value = "单位",index = 4)
	private String unit;
    @ExcelProperty(value = "产品类型",index = 5)
	private Integer flag;
    @ExcelProperty(value = "日期",index = 6)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date statisticsTime;
}

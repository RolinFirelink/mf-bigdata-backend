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

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 城市产品生产统计表Excel
 * @author cgli
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Data
@ToString
public class ProductCountExcel{
    @ExcelProperty(value = "唯一ID",index = 0)
    @TableId(type = IdType.AUTO)
    private Long id;
    @ExcelProperty(value = "产品类型",index = 1)
	private Integer flag;
    @ExcelProperty(value = "产品名称",index = 2)
	private String produce;
    @ExcelProperty(value = "城市",index = 3)
	private String city;
    @ExcelProperty(value = "生产规模",index = 4)
	private BigDecimal productionScale;
    @ExcelProperty(value = "规模单位",index = 5)
	private String unit;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = "统计时间",index = 6)
	private Date time;
}

package com.arg.smart.web.product.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 产品价格表Excel
 * @author cgli
 * @date: 2023-07-01
 * @version: V1.0.0
 */
@Data
@ToString
public class ProductPriceExcel {
    @ExcelProperty(value = "唯一ID",index = 0)
    @TableId(type = IdType.AUTO)
    private Long id;
    @ExcelProperty(value = "日期",index = 1)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date time;
    @ExcelProperty(value = "产品",index = 2)
	private String product;
    @ExcelProperty(value = "地区",index = 3)
	private String region;
    @ExcelProperty(value = "价格",index = 4)
	private BigDecimal price;
    @ExcelProperty(value = "涨幅",index = 5)
	private BigDecimal lifting;
    @ExcelProperty(value = "关联产品",index = 6)
	private Integer flag;
    @ExcelProperty(value = "计量单位",index = 7)
	private String unit;
}

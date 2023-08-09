package com.arg.smart.web.product.entity.vo;

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
 * @description: 产品批发价格表Excel
 * @author cgli
 * @date: 2023-07-08
 * @version: V1.0.0
 */
@Data
@ToString
public class ProductMarketPriceExcel {
    @ExcelProperty(value = "唯一ID",index = 0)
    @TableId(type = IdType.AUTO)
    private Long id;
    @ExcelProperty(value = "批发市场",index = 1)
	private String market;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "记录日期",index = 2)
	private Date recordDate;
    @ExcelProperty(value = "最低价格",index = 3)
	private BigDecimal bottomPrice;
    @ExcelProperty(value = "最高价格",index = 4)
	private BigDecimal topPrice;
    @ExcelProperty(value = "平均价格",index = 5)
	private BigDecimal averagePrice;
    @ExcelProperty(value = "产品名",index = 6)
	private String name;
    @ExcelProperty(value = "区分字段",index = 7)
	private Integer flag;
    @ExcelProperty(value = "计量单位",index = 8)
	private String unit;
}

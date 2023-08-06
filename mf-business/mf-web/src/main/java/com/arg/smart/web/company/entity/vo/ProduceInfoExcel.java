package com.arg.smart.web.company.entity.vo;

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
 * @description: 企业生产信息
 * @author cgli
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Data
@ToString
public class ProduceInfoExcel {
    @ExcelProperty(value = "id",index = 0)
    @TableId(type = IdType.AUTO)
    private Long id;
    @ExcelProperty(value = "企业ID",index = 1)
	private Long companyId;
    @ExcelProperty(value = "产品类型",index = 2)
	private Integer flag;
    @ExcelProperty(value = "均价",index = 3)
	private BigDecimal price;
    @ExcelProperty(value = "生产规模",index = 4)
	private Integer scale;
    @ExcelProperty(value = "生产规模单位",index = 5)
	private String scaleUnit;
    @ExcelProperty(value = "均价单位",index = 6)
	private String priceUnit;
    @ExcelProperty(value = "预计上市时间",index = 7)
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date estimatedLaunchDate;
    @ExcelProperty(value = "标识",index = 8)
	private String identifiers;
    @ExcelProperty(value = "预计产量",index = 9)
	private BigDecimal projectedProduction;
    @ExcelProperty(value = "产量单位",index = 10)
    private String productUnit;
    @ExcelProperty(value = "产品名",index = 11)
    private String product;
}

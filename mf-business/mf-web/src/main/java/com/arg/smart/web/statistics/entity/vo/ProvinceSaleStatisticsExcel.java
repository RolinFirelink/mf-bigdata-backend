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
 * @author cgli
 * @description: 省份销售数据
 * @date: 2023-07-15
 * @version: V1.0.0
 */
@Data
@ToString
public class ProvinceSaleStatisticsExcel {
    @ExcelProperty(value = "唯一ID",index = 0)
    @TableId(type = IdType.AUTO)
    private Long id;
    @ExcelProperty(value = "省份",index = 1)
    private String province;
    @ExcelProperty(value = "平均价格",index = 2)
    private BigDecimal averagePrice;
    @ExcelProperty(value = "销售量",index = 3)
    private String sales;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ExcelProperty(value = "统计时间",index = 4)
    private Date statisticalTime;
    @ExcelProperty(value = "产品类型",index = 5)
    private Integer flag;
    @ExcelProperty(value = "价格单位",index = 6)
    private String priceUnit;
    @ExcelProperty(value = "销量单位",index = 7)
    private String saleUnit;

}

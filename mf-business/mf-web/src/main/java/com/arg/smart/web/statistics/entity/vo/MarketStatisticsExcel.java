package com.arg.smart.web.statistics.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author cgli
 * @description: 市场行情统计表
 * @date: 2023-07-17
 * @version: V1.0.0
 */
@Data
@ToString
public class MarketStatisticsExcel {
    @ExcelProperty(value = "唯一ID",index = 0)
    @TableId(type = IdType.AUTO)
    private Long id;
    @ExcelProperty(value = "产地名称",index = 1)
    private String city;
    @ExcelProperty(value = "产量",index = 2)
    private Integer yield;
    @ExcelProperty(value = "产量单位",index = 3)
    private String yieldUnit;
    @ExcelProperty(value = "平均价格（元）",index = 4)
    private BigDecimal averagePrice;
    @ExcelProperty(value = "销售额（万元）",index = 5)
    private BigDecimal sales;
    @ExcelProperty(value = "产品类型",index = 6)
    private Integer flag;
}

package com.arg.smart.web.report.entity.vo;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author GlowingTree
 * @Description 价格预测值对象
 * @Date 8/24/2023 9:02 PM
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PricePredictVO {
    @ApiModelProperty("产品名称")
    private String product;

    @ApiModelProperty("记录时间")
    @DateTimeFormat("yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;

    @ApiModelProperty("产品平均价格")
    private BigDecimal avgPrice;

    @ApiModelProperty("产品单位")
    private String unit;
}

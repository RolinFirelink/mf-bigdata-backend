package com.arg.smart.web.report.entity.vo;

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
    @ApiModelProperty("记录时间")
    private Date date;
    @ApiModelProperty("平均价格")
    private BigDecimal avgPrice;
}

package com.arg.smart.web.product.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@Data
public class PriceTemp {

    private Integer flag;
    //最新的价格记录：  价格   涨幅   上次的价格 = 价格 / （1 + 涨幅）
    //指数 = 价格 / 昨日价格 * 100
    private Integer temp;

    //改变的价格 = 昨日价格 - 价格
    private BigDecimal changePrice;

    private String unit;
}

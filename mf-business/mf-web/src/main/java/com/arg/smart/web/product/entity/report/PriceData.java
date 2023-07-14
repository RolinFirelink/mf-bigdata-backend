package com.arg.smart.web.product.entity.report;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PriceData {

    private Date time;

    private BigDecimal value;

    private String unit;
}

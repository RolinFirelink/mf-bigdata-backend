package com.arg.smart.web.product.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zzy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseProduceInfoVO {
    private String baseName;
    private String code;
    private BigDecimal scale;
    private Integer quantity;
    private Date time;
    private String address;
}

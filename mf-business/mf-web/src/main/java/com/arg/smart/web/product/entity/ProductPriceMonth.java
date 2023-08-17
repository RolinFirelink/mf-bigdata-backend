package com.arg.smart.web.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@TableName("sh_product_price_month")
@Data
public class ProductPriceMonth {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private LocalDate time;

    private String product;

    private String region;

    private BigDecimal price;

    private String lifting;

    private Integer flag;

    private String unit;
}

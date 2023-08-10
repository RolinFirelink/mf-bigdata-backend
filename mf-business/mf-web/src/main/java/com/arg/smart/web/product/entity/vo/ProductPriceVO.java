package com.arg.smart.web.product.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 产品价格表
 * @author Hanyuehong
 * @date: 2023-07-12
 * @version: V1.0.0
 */
@Data
public class ProductPriceVO {
    @ApiModelProperty(value = "时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date time;
    @ApiModelProperty(value = "最高价格")
    private BigDecimal maxPrice;
    @ApiModelProperty(value = "最低价格")
    private BigDecimal minPrice;
}

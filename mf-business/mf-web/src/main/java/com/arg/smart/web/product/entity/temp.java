package com.arg.smart.web.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;
import org.apache.poi.hpsf.Decimal;

@Data
@TableName("sh_product_price")
@Accessors(chain = true)
@ApiModel(value = "sh_product_price对象", description = "产品价格表")
public class temp {
    private String market;
    private Decimal lat;
    private Decimal lng;

}

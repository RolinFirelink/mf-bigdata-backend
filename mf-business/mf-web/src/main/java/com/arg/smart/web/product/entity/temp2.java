package com.arg.smart.web.product.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@TableName("sh_product_price")
@Accessors(chain = true)
@ApiModel(value = "sh_product_price对象", description = "产品价格表")
public class temp2 {
    private String baseName;

}

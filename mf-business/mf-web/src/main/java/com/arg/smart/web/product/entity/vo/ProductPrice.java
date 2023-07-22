package com.arg.smart.web.product.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.poi.hpsf.Date;
import java.math.BigDecimal;

/**
 * @description: 产品价格表
 * @author Hanyuehong
 * @date: 2023-07-12
 * @version: V1.0.0
 */
@Data
@ToString
@Accessors(chain = true)
@TableName("sh_product_price")
public class ProductPrice {
    @ApiModelProperty(value = "时间")
    private Date time;
    @ApiModelProperty(value = "产品")
    private String product;
    @ApiModelProperty(value = "价格")
    private BigDecimal price;
    @ApiModelProperty(value = "计量单位")
    private String unit;
}

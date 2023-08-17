package com.arg.smart.web.product.entity.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author zzy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvgPriceVO {

    @ApiModelProperty(value = "日期")
    private String time;
    @ApiModelProperty(value = "价格（xx元/一株）（xx元/斤）")
    private BigDecimal price;
    @ApiModelProperty(value = "关联产品")
    private Integer flag;

}

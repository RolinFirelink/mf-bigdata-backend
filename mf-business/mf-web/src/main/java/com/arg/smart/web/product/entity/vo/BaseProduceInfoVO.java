package com.arg.smart.web.product.entity.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zzy
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BaseProduceInfoVO {
    @ApiModelProperty(value = "基地名字")
    private String baseName;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "基地规模")
    private BigDecimal scale;

    @ApiModelProperty(value = "基地产品产量")
    private Integer quantity;

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "预计时间")
    private Date time;

    @ApiModelProperty(value = "基地位置")
    private String address;
}

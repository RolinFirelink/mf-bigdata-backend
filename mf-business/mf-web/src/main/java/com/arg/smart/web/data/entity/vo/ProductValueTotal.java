package com.arg.smart.web.data.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;

@Data
@ToString
@Accessors(chain = true)
@AllArgsConstructor
public class ProductValueTotal {
    @ApiModelProperty("区分产品")
    private Integer flag;
    @ApiModelProperty("城市名")
    private String city;
    @ApiModelProperty("产品总产值")
    private Integer productWeightTotal;
    @ApiModelProperty("年份")
    private Date date;

}

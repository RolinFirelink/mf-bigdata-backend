package com.arg.smart.web.docking.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Accessors(chain = true)
@ApiModel(value = "竞品销售数据", description = "竞品销售数据")
public class SellDataVO {

    @ApiModelProperty(value = "省份")
    private String provinceName;

    @ApiModelProperty(value = "该省占全国比重")
    private Double cntRatio;

    @ApiModelProperty(value = "平均价格")
    private BigDecimal avgPrice;
}

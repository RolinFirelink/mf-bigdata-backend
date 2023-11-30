package com.arg.smart.web.data.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Data
@ToString
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class CompanyProductVO {

    @ApiModelProperty(value = "公司名称")
    private String companyName;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "产品价格")
    private BigDecimal productValue;
    @ApiModelProperty(value = "产品销量")
    private Integer productSales;
}

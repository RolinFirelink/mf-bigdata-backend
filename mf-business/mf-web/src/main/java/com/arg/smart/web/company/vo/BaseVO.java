package com.arg.smart.web.company.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BaseVO {
    @ApiModelProperty(value = "基地名称")
    private String baseName;
    @ApiModelProperty(value = "联系人")
    private String contacts;
    @ApiModelProperty(value = "联系电话")
    private String contactPhone;
    @ApiModelProperty(value = "基地规模")
    private BigDecimal scale;
    @ApiModelProperty(value = "产品类型")
    private String productType;
    @ApiModelProperty(value = "地址")
    private String address;
}

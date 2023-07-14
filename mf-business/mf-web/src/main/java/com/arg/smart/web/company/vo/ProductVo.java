package com.arg.smart.web.company.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductVo {
    @ApiModelProperty(value = "预计产量")
    private String  projectedProduction ;
    @ApiModelProperty(value = "价格单位")
    private String  productUnit ;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "预计上市时间")
    private Date estimatedLaunchDate;

    private String iphoneNumber;

    private String baseName;

    private String latLongItude;



}
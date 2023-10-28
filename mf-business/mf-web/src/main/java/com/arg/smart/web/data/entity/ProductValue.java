package com.arg.smart.web.data.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("sh_sales_flow")
@Accessors(chain = true)
@ApiModel(value = "sh_product_value", description = "采购导图对象")
public class ProductValue {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "产品类型")
    private Integer flag;
    @ApiModelProperty(value = "城市名")
    private String city;
    @ApiModelProperty(value = "上市时间")
    private String upDate;
    @ApiModelProperty(value = "规模单位")
    private String productName;
    @ApiModelProperty(value = "产品预计 出栏量/出产量 ")
    private Integer productWeight;
    @ApiModelProperty(value = "预计产值")
    private Integer productValue;
    @ApiModelProperty(value = "生产面积/存栏量")
    private Integer productScale;
    @ApiModelProperty(value = "区域地")
    private String region;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "年份")
    private Date date;

}

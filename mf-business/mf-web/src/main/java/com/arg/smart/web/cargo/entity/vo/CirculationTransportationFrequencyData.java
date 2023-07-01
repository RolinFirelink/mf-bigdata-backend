package com.arg.smart.web.cargo.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ToString
@Accessors(chain = true)
public class CirculationTransportationFrequencyData {
    @ApiModelProperty(value = "产品类别")
    private Integer flag;
    @ApiModelProperty(value = "货运批次号")
    private String batchNumber;
    @ApiModelProperty(value = "货运单号")
    private String oddNumbers;
    @ApiModelProperty(value = "货运单位")
    private String shippingUnit;
    @ApiModelProperty(value = "自定义拓展字段JSON结构(备注)")
    private String extendField;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "收货时间")
    private Date receivingTime;


}

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
import java.util.List;

@Data
@ToString
@Accessors(chain = true)
@TableName("sh_product_circulation_data")
public class CirculationTransportationFrequencyDataList {
    @ApiModelProperty(value = "产品类别")
    private Integer flag;
    @ApiModelProperty(value = "运输平均价格")
    private BigDecimal averagePrice;
    @ApiModelProperty(value = "质量单位")
    String massUnit;
    @ApiModelProperty(value = "货运运输重量")
    private double transportationQuantityTall;
    @ApiModelProperty(value = "运输次数")
    private Integer transportationTimes;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "收货时间")
    private Date receivingTime;
    @ApiModelProperty(value = "各订单集合")
    private List<CirculationTransportationFrequencyData> circulationTransportationFrequencyDataList;

}

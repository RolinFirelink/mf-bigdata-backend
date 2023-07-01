package com.arg.smart.web.cargo.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@ToString
@Accessors(chain = true)
public class ShipmentOrderData {
    @ApiModelProperty(value = "产品类别")
    Integer flag;
    @ApiModelProperty(value = "产品名称")
    String productName;
    @ApiModelProperty(value = "订单ID")
    private Long orderId;
    @ApiModelProperty(value = "发货地址")
    private String shippingLocation;
    @ApiModelProperty(value = "收货地址")
    private String receivingLocation;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发货时间")
    private Date deliveryTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "收货时间")
    private Date receivingTime;


}

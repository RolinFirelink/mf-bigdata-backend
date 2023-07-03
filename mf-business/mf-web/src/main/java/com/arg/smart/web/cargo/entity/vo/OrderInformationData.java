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
@TableName("sh_product_circulation_data")
public class OrderInformationData {
    @ApiModelProperty(value = "产品类别")
    private Integer flag;
    @ApiModelProperty(value = "产品名称")
    private String productName;
    @ApiModelProperty(value = "订单ID")
    private Long orderId;
    @ApiModelProperty(value = "承运商")
    private String companyName;
    @ApiModelProperty(value = "运输方式")
    private String modeTransport;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发货时间")
    private Date deliveryTime;

}

package com.arg.smart.web.cargo.entity.vo;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Date;


@Data
@ToString
@Accessors(chain = true)
@TableName("sh_product_circulation_data")
public class OrderInformationList {

    @ApiModelProperty(value = "发货人")
    private String shipper;

    @ApiModelProperty(value = "发货地点")
    private String shippingLocation;

    @ApiModelProperty(value = "发货区域编码")
    private String shippingAreaCode;

    @ApiModelProperty(value = "收货人")
    private String receiver;

    @ApiModelProperty(value = "收货地点")
    private String receivingLocation;

    @ApiModelProperty(value = "收货区域编码")
    private String receivingAreaCode;

    @ApiModelProperty(value = "自定义拓展字段JSON结构")/*备注*/
    private String extendField;
}

package com.arg.smart.web.order.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 订单数据主表
 * @author cgli
 * @date: 2023-05-19
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("订单数据主表请求参数")
public class ReqOrder {
    @ApiModelProperty(value = "供应企业编号")
    private Long companyId;
    @ApiModelProperty(value = "供应企业编码")
    private String companyNo;
    @ApiModelProperty(value = "供应企业名称")
    private String vendorName;
    @ApiModelProperty(value = "采购商名称")
    private String buyerName;
    @ApiModelProperty(value = "订单类型")
    private Integer category;
    @ApiModelProperty(value = "订单状态，1=企业待发货，2=企业送货送货中，3=客户待收货，4=客户已收货， 5=客户已退货，6=客户退货物流中，7=企业已收到退货")
    private Integer status;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "订单创建时间")
    private Date createStartTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "订单完成时间")
    private Date createEndTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "订单完成时间")
    private Date finishStartTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "订单完成时间")
    private Date finishEndTime;
    @ApiModelProperty(value = "模块编号，用于区分不同模块的数据，1=肉鸡、2=柑橘、3=兰花、4=对虾、5=菜心、6=预制菜")
    private Integer flag;
}

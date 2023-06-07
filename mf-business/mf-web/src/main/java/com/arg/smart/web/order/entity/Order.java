package com.arg.smart.web.order.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author cgli
 * @description: 订单数据主表
 * @date: 2023-05-19
 * @version: V1.0.0
 */
@Data
@ToString
@TableName("sh_order")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_order对象", description = "订单数据主表")
public class Order extends BaseEntity<Long> implements Serializable {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "订单类型，1=生产订单，2=出库订单，3=采购订单，4=销售订单，5=企业退货订单， 6=消费者退货订单（生产订单指生产制造企业加工的订单数据或基地采收记录、出库订单就是出库记录、 采购订单是批发商或零售商向厂家采购的数据、销售订单是消费者购买订单、退货订单是消费者或批发商、零售商退货的订单）")
    private Integer category;
    @ApiModelProperty(value = "供应企业ID")
    private Long vendorId;
    @ApiModelProperty(value = "供应企业名称")
    private String vendorName;
    @ApiModelProperty(value = "采购商ID")
    private Long buyerId;
    @ApiModelProperty(value = "采购商名称")
    private String buyerName;
    @ApiModelProperty(value = "订单状态，1=企业待发货，2=企业送货送货中，3=客户待收货，4=客户已收货， 5=客户已退货，6=客户退货物流中，7=企业已收到退货")
    private Integer status;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "订单创建时间")
    private Date startTime;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "订单完成时间")
    private Date finishTime;
    @ApiModelProperty(value = "模块编号，用于区分不同模块的数据，1=肉鸡、2=柑橘、3=兰花、4=对虾、5=菜心、6=预制菜")
    private Integer flag;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "逻辑删除，0=未删除，1=已删除")
    @TableLogic
    private Integer deleteFlag;
    @TableField(exist = false)
    private List<OrderDetail> orderDetailList;
}

package com.arg.smart.web.order.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @description: 订单数据明细表
 * @author cgli
 * @date: 2023-05-22
 * @version: V1.0.0
 */
@Data
@TableName("sh_order_detail")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_order_detail对象", description = "订单数据明细表")
public class OrderDetail extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "订单ID")
	private Long orderId;
    @ApiModelProperty(value = "产品编号")
	private Long materialId;
    @ApiModelProperty(value = "产品名称")
	private String materialName;
    @ApiModelProperty(value = "销售数量")
	private Long salesQuantity;
    @ApiModelProperty(value = "计量单位")
    private String unit;
    @ApiModelProperty(value = "销售单价")
	private BigDecimal salesAmount;
    @ApiModelProperty(value = "逻辑删除，0=未删除，1=已删除")
    @TableLogic
	private Integer deleteFlag;

}

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
    @ApiModelProperty(value = "父表编号")
	private Long parentId;
    @ApiModelProperty(value = "产品编号")
	private Long productId;
    @ApiModelProperty(value = "产品名称")
	private String productName;
    @ApiModelProperty(value = "产品图片地址")
	private String productAvatar;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "销售时间")
	private Date salesTime;
    @ApiModelProperty(value = "销售数量")
	private Long salesQuantity;
    @ApiModelProperty(value = "销售单价")
	private BigDecimal salesAmount;
    @ApiModelProperty(value = "销售批次")
	private Long salesBatchNumber;
    @ApiModelProperty(value = "计量单位")
	private String unit;
    @ApiModelProperty(value = "条目状态，1=企业待发货，2=企业送货送货中，3=客户待收货，4=客户已收货，5=客户已退货， 6=客户退货物流中，7=企业已收到退货")
	private Integer status;
    @ApiModelProperty(value = "条目是否完成，0=未完成，1=已完成")
	private Integer completeFlag;
    @ApiModelProperty(value = "备注")
	private String remark;
    @ApiModelProperty(value = "自定义拓展信息 JSON 结构")
	private String extendField;
    @ApiModelProperty(value = "是否开启批号，0=否，1=是")
	private Integer enableBatchNumber;
    @ApiModelProperty(value = "逻辑删除，0=未删除，1=已删除")
    @TableLogic
	private Integer deleteFlag;

}

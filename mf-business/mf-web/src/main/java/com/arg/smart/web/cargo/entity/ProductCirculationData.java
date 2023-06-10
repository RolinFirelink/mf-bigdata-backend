package com.arg.smart.web.cargo.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 货运表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
@Data
@ToString
@TableName("sh_product_circulation_data")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_product_circulation_data对象", description = "货运表")
public class ProductCirculationData extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "生产日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date dateManufacture;
    @ApiModelProperty(value = "生产批次")
	private String productionBatch;
    @ApiModelProperty(value = "生产厂家")
	private String manufacturer;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "发货时间")
	private Date deliveryTime;
    @ApiModelProperty(value = "发货单位")
	private String forwardingUnit;
    @ApiModelProperty(value = "发货人")
	private String shipper;
    @ApiModelProperty(value = "发货人电话")
	private String shipperPhoneNumber;
    @ApiModelProperty(value = "发货地点")
	private String shippingLocation;
    @ApiModelProperty(value = "发货区域编码")
	private String shippingAreaCode;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "收货时间")
	private Date receivingTime;
    @ApiModelProperty(value = "收货单位")
	private String consignee;
    @ApiModelProperty(value = "收货人")
	private String receiver;
    @ApiModelProperty(value = "收货人电话")
	private String receiverPhone;
    @ApiModelProperty(value = "收货地点")
	private String receivingLocation;
    @ApiModelProperty(value = "收货区域编码")
	private String receivingAreaCode;
    @ApiModelProperty(value = "货运物流中转信息")
	private String freightLogisticsTransferInformation;
    @ApiModelProperty(value = "运输方式")
	private String modeTransport;
    @ApiModelProperty(value = "运输价格")
	private BigDecimal transportationPrice;
    @ApiModelProperty(value = "运输时间")
	private Integer transportationTime;
    @ApiModelProperty(value = "时间单位")
	private String timeUnit;
    @ApiModelProperty(value = "运输数量")
	private Long transportationQuantity;
    @ApiModelProperty(value = "货运批次号")
	private String batchNumber;
    @ApiModelProperty(value = "货运单位")
	private String shippingUnit;
    @ApiModelProperty(value = "货运单号")
	private String oddNumbers;
    @ApiModelProperty(value = "企业（承运商）ID")
	private Long companyId;
    @ApiModelProperty(value = "自定义拓展字段JSON结构")
	private String extendField;
    @ApiModelProperty(value = "删除标识")
	private Integer deleteFlag;
    @ApiModelProperty(value = "订单ID")
	private Long orderId;
    @ApiModelProperty(value = "业务类型（生产、销售等）")
	private Integer businessType;
    @TableField(exist = false)
    private String companyName;
}

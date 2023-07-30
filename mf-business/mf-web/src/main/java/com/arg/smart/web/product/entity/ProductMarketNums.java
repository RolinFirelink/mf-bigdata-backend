package com.arg.smart.web.product.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @description: 批发市场采购量表
 * @author cgli
 * @date: 2023-07-30
 * @version: V1.0.0
 */
@Data
@TableName("sh_product_market_nums")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_product_market_nums对象", description = "批发市场采购量表")
public class ProductMarketNums extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "产品名")
	private String name;
    @ApiModelProperty(value = "区分字段")
	private Integer flag;
    @ApiModelProperty(value = "采购数量")
	private Double purchaseNums;
    @ApiModelProperty(value = "期望货源地")
	private String expectedSource;
    @ApiModelProperty(value = "发布人")
	private String publisher;
    @ApiModelProperty(value = "认证情况")
	private String authenticationStatus;
    @ApiModelProperty(value = "计量单位")
	private String unit;
    @ApiModelProperty(value = "采购时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private Date purchaseTime;
    @ApiModelProperty(value = "采购轮次")
	private String purchaseRound;
    @ApiModelProperty(value = "收货地")
	private String receiptPlace;
    @ApiModelProperty(value = "补充说明")
	private String additionalNotes;
    @ApiModelProperty(value = "浏览次数")
	private Integer viewNums;
    @ApiModelProperty(value = "规格品质")
	private String quality;
}

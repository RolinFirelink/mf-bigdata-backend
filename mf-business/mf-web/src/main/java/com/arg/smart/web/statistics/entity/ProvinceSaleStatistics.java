package com.arg.smart.web.statistics.entity;

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

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 省份销售数据
 * @author cgli
 * @date: 2023-07-15
 * @version: V1.0.0
 */
@Data
@TableName("sh_province_sale_statistics")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_province_sale_statistics对象", description = "省份销售数据")
public class ProvinceSaleStatistics extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "省份")
	private String province;
    @ApiModelProperty(value = "平均价格")
	private BigDecimal averagePrice;
    @ApiModelProperty(value = "销售量")
	private String sales;
    @ApiModelProperty(value = "逻辑删除")
	private Integer deleteFlag;
    @ApiModelProperty(value = "统计时间")
	private Date statisticalTime;
    @ApiModelProperty(value = "产品类型")
    private Integer flag;
    @ApiModelProperty(value = "价格单位")
    private String priceUnit;
    @ApiModelProperty(value = "销量单位")
    private String saleUnit;

}

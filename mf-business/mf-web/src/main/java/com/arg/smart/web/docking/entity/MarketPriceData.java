package com.arg.smart.web.docking.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 惠农网批发市场数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Data
@TableName("cn_pifa_price")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "cn_pifa_price对象", description = "惠农网批发市场数据")
public class MarketPriceData extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "日期")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
    @ApiModelProperty(value = "产品分类")
	private Integer flag;
    @ApiModelProperty(value = "平均价格")
	private BigDecimal avgPrice;
    @ApiModelProperty(value = "批发市场")
	private String marketName;
    @ApiModelProperty(value = "单位")
	private String unit;
    @ApiModelProperty(value = "逻辑删除")
	private Integer deleteFlag;
}

package com.arg.smart.web.data.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 企业销售数据
 * @author cgli
 * @date: 2023-08-24
 * @version: V1.0.0
 */
@Data
@TableName("sh_company_sales")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_company_sales对象", description = "企业销售数据")
public class CompanySales extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "日期")
	private Date time;
    @ApiModelProperty(value = "产品")
	private String product;
    @ApiModelProperty(value = "产品类型")
	private Integer flag;
    @ApiModelProperty(value = "销售额")
	private BigDecimal sales;
    @ApiModelProperty(value = "销售量")
	private BigDecimal salesNum;
    @ApiModelProperty(value = "")
	private Integer deleteFlag;
}

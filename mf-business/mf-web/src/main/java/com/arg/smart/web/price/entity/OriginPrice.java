package com.arg.smart.web.price.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @author cgli
 * @description: 产地价格
 * @date: 2023-07-10
 * @version: V1.0.0
 */
@Data
@TableName("sh_origin_price")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_origin_price对象", description = "产地价格")
public class OriginPrice extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "公司ID")
    private Long companyId;
    @ApiModelProperty(value = "产品类型")
    private Integer flag;
    @ApiModelProperty(value = "主要销售城市")
    private String cityCode;
    @ApiModelProperty(value = "价格")
    private BigDecimal price;
    @ApiModelProperty(value = "价格单位")
    private String unit;
    @TableField(exist = false)
    @ApiModelProperty(value = "公司名")
    private String companyName;
    @TableField(exist = false)
    @ApiModelProperty("主要销售城市")
    private String mainCity;
}

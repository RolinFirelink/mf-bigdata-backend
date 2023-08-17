package com.arg.smart.web.data.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * @description: 销售流向
 * @author cgli
 * @date: 2023-07-18
 * @version: V1.0.0
 */
@Data
@TableName("sh_sales_flow")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_sales_flow对象", description = "销售流向")
public class SalesFlow extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "起始行政区划代码")
    private String startAreaCode;
    @ApiModelProperty(value = "起点纬度")
    private BigDecimal startLat;
    @ApiModelProperty(value = "起点经度")
    private BigDecimal startLng;
    @ApiModelProperty(value = "终点行政区划代码")
    private String endAreaCode;
    @ApiModelProperty(value = "终点纬度")
    private BigDecimal endLat;
    @ApiModelProperty(value = "终点经度")
    private BigDecimal endLng;
    @ApiModelProperty(value = "产品类型")
    private Integer flag;
    @ApiModelProperty(value = "逻辑删除")
    private Integer deleteFlag;
}

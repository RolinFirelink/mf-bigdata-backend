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

/**
 * @description: 城市销售量表
 * @author cgli
 * @date: 2023-07-17
 * @version: V1.0.0
 */
@Data
@TableName("sh_city_sale_statistics")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_city_sale_statistics对象", description = "城市销售量表")
public class CitySaleStatistics extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "城市")
	private String city;
    @ApiModelProperty(value = "销售量")
	private Integer sales;
    @ApiModelProperty(value = "单位")
	private String unit;
    @ApiModelProperty(value = "逻辑删除")
	private Integer deleteFlag;
    @ApiModelProperty(value = "产品类型")
	private Integer flag;
}

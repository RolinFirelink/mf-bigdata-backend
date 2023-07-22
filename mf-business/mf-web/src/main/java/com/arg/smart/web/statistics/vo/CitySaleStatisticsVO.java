package com.arg.smart.web.statistics.vo;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;

/**
 * @author cgli
 * @description: 城市销售数据
 * @date: 2023-07-19
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "城市销售数据", description = "城市销售数据")
public class CitySaleStatisticsVO {
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "销售量")
    private Integer sales;
    @ApiModelProperty(value = "单位")
    private String unit;
}
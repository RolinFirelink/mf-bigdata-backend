package com.arg.smart.web.docking.entity;

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
 * @description: 惠农网广东月销售数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Data
@TableName("cn_sell2")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "cn_sell2对象", description = "惠农网广东月销售数据")
public class SellDataGuangdong extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "月份")
	private String date;
    @ApiModelProperty(value = "产品分类")
	private Integer flag;
    @ApiModelProperty(value = "卖家省份")
	private String saleProvinceName;
    @ApiModelProperty(value = "买家省份")
	private String buyProvinceName;
    @ApiModelProperty(value = "买家占广东省比重")
	private Double cntRatio;
    @ApiModelProperty(value = "逻辑删除")
	private Integer deleteFlag;
}

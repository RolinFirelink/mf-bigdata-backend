package com.arg.smart.web.docking.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;

/**
 * @description: 惠农网省份月采购数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Data
@TableName("cn_inquiry")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "cn_inquiry对象", description = "惠农网省份月采购数据")
public class InquiryData extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "月份")
	private String date;
    @ApiModelProperty(value = "产品分类")
	private Integer flag;
    @ApiModelProperty(value = "省份")
	private String provinceName;
    @ApiModelProperty(value = "该省占全国比重")
	private Double cntRatio;

    @ApiModelProperty(value =  "产品")
    private String product;

    @TableLogic
    @ApiModelProperty(value = "逻辑删除")
	private Integer deleteFlag;
}

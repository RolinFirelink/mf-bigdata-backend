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

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 产品生产表
 * @author cgli
 * @date: 2023-05-19
 * @version: V1.0.0
 */
@Data
@TableName("sh_material_produce")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_material_produce对象", description = "产品生产表")
public class MaterialProduce extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "公司id")
	private Long companyId;
    @ApiModelProperty(value = "产品id")
	private Long materialId;
    @ApiModelProperty(value = "生产批次")
	private Integer batch;
    @ApiModelProperty(value = "生产规模")
	private BigDecimal productionScale;
    @ApiModelProperty(value = "规模单位")
	private String unit;
    @ApiModelProperty(value = "逻辑删除")
	private Integer deletedFlag;
    @ApiModelProperty(value = "产品编号")
	private Long number;
    @ApiModelProperty(value = "产品名称")
	private String name;
    @ApiModelProperty(value = "产品类型id")
	private Long categoryId;
    @ApiModelProperty(value = "单位产量估算")
	private Integer productEstimate;
    @ApiModelProperty(value = "预计上市产量")
	private Integer marketEstimate;
    @ApiModelProperty(value = "预计上市时间")
	private Date timeEstimate;
    @ApiModelProperty(value = "基地id")
	private Long baseId;
    @ApiModelProperty(value = "生产数量")
	private Integer quantity;
    @ApiModelProperty(value = "是否卖出")
	private Integer isSell;
    @ApiModelProperty(value = "区分字段")
	private Integer flag;
}

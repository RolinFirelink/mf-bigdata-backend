package com.arg.smart.web.report.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author GlowingTree
 * @Description 农产品产销形势分析报告实体类
 * @Date 27/08/2023 21:06
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sh_produce_and_sale_report")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_produce_and_sale_report对象", description = "农产品产销形势分析报告")
@Builder
public class ProduceAndSale extends BaseEntity<Long> implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键 ID")
    private Long id;
    @ApiModelProperty("区分字段")
    private Integer flag;
    @ApiModelProperty("年份")
    private Integer year;
    @ApiModelProperty("月份")
    private Integer month;
    @ApiModelProperty("报告内容")
    private String content;
    @ApiModelProperty("上报时间")
    private Date reportTime;
}

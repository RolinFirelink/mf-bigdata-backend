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
 * @Description 农产品竞品分析报告对象
 * @Date 27/08/2023 21:10
 * @Version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("sh_good_analyse_report")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_good_analyse_report对象", description = "农产品竞品分析报告")
@Builder
public class GoodAnalyse extends BaseEntity<Long> implements Serializable {
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键 ID")
    private Long id;
    @ApiModelProperty("区分字段")
    private Integer flag;
    @ApiModelProperty("待分析的第一个产品")
    private String firstGood;
    @ApiModelProperty("待分析的第二个产品")
    private String secondGood;
    @ApiModelProperty("年份")
    private Integer year;
    @ApiModelProperty("月份")
    private Integer month;
    @ApiModelProperty("报告内容")
    private String content;
    @ApiModelProperty("上报时间")
    private Date reportTime;
}

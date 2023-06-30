package com.arg.smart.web.customer.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 热词表
 * @author zsj
 * @date: 2023-06-29
 * @version: V1.0.0
 */
@Data
@TableName("sh_hot_word")
@Accessors(chain = true)
@ApiModel(value = "sh_hot_word对象", description = "热词表")
public class HotWord  {

    @ApiModelProperty(value = "唯一ID")
    @TableId
    private Long id;

    @ApiModelProperty(value = "热词")
    private String name;

    @ApiModelProperty(value = "提及次数")
    private Integer count;

    @ApiModelProperty(value = "情绪(1:正面，0：中性，-1：负面)")
    private Integer sentiment;

    @ApiModelProperty(value = "统计时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date statisticalTime;

}

package com.arg.smart.web.customer.entity;

import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

/**
 * @description: 热词表
 * @author cgli
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Data
@TableName("sh_hot_word")
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "sh_hot_word对象", description = "热词表")
@NoArgsConstructor
@AllArgsConstructor
public class HotWord extends BaseEntity<Long> {
    @ApiModelProperty(value = "唯一ID")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "热词")
	private String name;
    @ApiModelProperty(value = "提及次数")
	private Integer count;
    @ApiModelProperty(value = "情绪（-1:负面，0：中性，1：正面）")
	private Integer sentiment;
    @ApiModelProperty(value = "产品类型")
	private String flags;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "统计时间")
	private Date statisticalTime;
    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
	private Integer deleteFlag;

    public HotWord(String name, Integer count) {
        this.name = name;
        this.count = count;
    }
}

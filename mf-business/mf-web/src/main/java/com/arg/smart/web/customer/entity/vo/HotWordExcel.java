package com.arg.smart.web.customer.entity.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.arg.smart.common.core.entity.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 热词表Excel
 * @author cgli
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Data
@ToString
public class HotWordExcel{
    @ExcelProperty(value = "唯一ID", index = 0)
    @TableId(type = IdType.AUTO)
    private Long id;
    @ExcelProperty(value = "热词", index = 1)
	private String name;
    @ExcelProperty(value = "提及次数", index = 2)
	private Integer count;
    @ExcelProperty(value = "情绪（-1:负面，0：中性，1：正面）", index = 3)
	private Integer sentiment;
    @ExcelProperty(value = "产品类型", index = 4)
	private String flags;
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelProperty(value = "统计时间", index = 5)
	private Date statisticalTime;
}

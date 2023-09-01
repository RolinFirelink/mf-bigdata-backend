package com.arg.smart.web.report.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author GlowingTree
 * @Description 农产品产销形势报告条件查询类
 * @Date 27/08/2023 21:18
 * @Version 1.0
 */
@Data
@Accessors(chain = true)
@ApiModel("农产品产销形势报告表条件查询类")
public class ReqProduceAndSale {
    @ApiModelProperty("主键 ID")
    private Long id;
    @ApiModelProperty("区分字段")
    private Integer flag;
    @ApiModelProperty("年份")
    private Integer year;
    @ApiModelProperty("月份")
    private Integer month;
    @ApiModelProperty("起始时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @ApiModelProperty("结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
}

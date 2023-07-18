package com.arg.smart.web.order.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @Author GlowingTree
 * @Date 2023-6/7/2023-11:05 PM
 * @PackageName com.arg.smart.web.order.vo
 * @ClassName DurationQueryParam
 * @Description 时间段查询值对象
 * @Version 1.0
 */
@Data
@Builder
@ToString
@ApiModel(value = "时间段查询值对象", description = "时间段查询值对象")
public class DurationQueryParam {
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始时间")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "完成时间")
    private Date endTime;
}

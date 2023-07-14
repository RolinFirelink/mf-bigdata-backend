package com.arg.smart.web.product.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import net.sf.jsqlparser.util.validation.metadata.DatabaseException;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-01
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("产品价格表请求参数")
public class ReqProductPrice {
    @ApiModelProperty(value = "区分字段")
    private Integer flag;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "产品地区")
    private String region;

    @ApiModelProperty(value = "起止时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;

    @ApiModelProperty(value = "数据时间类型（1:月/2:季/3:半年/4:年）")
    private Integer timeType;
}

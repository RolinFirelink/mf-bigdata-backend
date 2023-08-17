package com.arg.smart.web.statistics.req;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author cgli
 * @description: 市场行情统计表
 * @date: 2023-07-17
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("市场行情统计表请求参数")
public class ReqMarketStatistics {

	@ApiModelProperty("产品类别")
	private Integer flag;

	@ApiModelProperty(value = "开始时间")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date startTime;

	@ApiModelProperty(value = "结束时间")
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date endTime;

	@ApiModelProperty("城市")
	private String city;
}

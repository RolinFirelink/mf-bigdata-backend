package com.arg.smart.web.report.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * @Author GlowingTree
 * @Description 农产品竞品分析值对象
 * @Date 8/25/2023 1:41 AM
 * @Version 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class GoodAnalyseVO {
	@ApiModelProperty("产品名称")
	private String name;
	@ApiModelProperty("产品简介图片 URL")
	private String picUrl;
	@ApiModelProperty("产品简介")
	private String summary;
	@ApiModelProperty("产品特性")
	private String feature;
}

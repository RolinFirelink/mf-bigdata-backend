package com.arg.smart.web.data.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 市场规模
 * @author cgli
 * @date: 2023-10-29
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("市场规模请求参数")
public class ReqMarketSize {

    @ApiModelProperty("产品类型")
    private Integer flag;

    @ApiModelProperty("年份")
    private Integer year;

    @ApiModelProperty("返回数量")
    private Integer count;

    @ApiModelProperty("终止年份")
    private Integer endYear;
}

package com.arg.smart.web.average.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("客户消费行为表请求参数")
public class ReqKeyword {
    @ApiModelProperty(value = "区分种类集合")
    private String flags;
    @ApiModelProperty(value = "关键词名称")
    private String keywordName;
    @ApiModelProperty(value = "是否要进行排序")
    private Boolean sort;
}

package com.arg.smart.web.docking.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@ApiModel("惠农网对接参数")
public class ReqPresentations {

    @ApiModelProperty("页码")
    private Integer page;
}

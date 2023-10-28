package com.arg.smart.web.docking.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 惠农网广东月供应数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("惠农网广东月供应数据请求参数")
public class ReqSupplyDataGuangDong {

    @ApiModelProperty("产品类别")
    private Integer flag;

    @ApiModelProperty("起始月份")
    private String startMonth;

    @ApiModelProperty("终止月份")
    private String endMonth;

}

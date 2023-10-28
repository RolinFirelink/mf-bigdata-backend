package com.arg.smart.web.docking.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 惠农网产品信息表
 * @author cgli
 * @date: 2023-09-26
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("惠农网产品信息表请求参数")
public class ReqMarketInfo {

    @ApiModelProperty("月份")
    private String dateMonth;
    @ApiModelProperty("分类3名称")
    private String cate3Name;

}

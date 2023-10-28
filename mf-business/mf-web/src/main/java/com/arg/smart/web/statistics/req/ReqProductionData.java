package com.arg.smart.web.statistics.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 地区生产宏观数据
 * @author cgli
 * @date: 2023-10-19
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("地区生产宏观数据请求参数")
public class ReqProductionData {


    @ApiModelProperty(value = "产品类别")
    private Integer flag;

    @ApiModelProperty(value = "城市")
    private String city;

}

package com.arg.smart.web.price.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 产地价格
 * @author cgli
 * @date: 2023-07-10
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("产地价格请求参数")
public class ReqOriginPrice {
    @ApiModelProperty(value = "公司ID")
    private Long companyId;
    @ApiModelProperty(value = "主要销售城市ID")
    private Long cityCode;
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    @ApiModelProperty(value = "产品类型")
    private Integer flag;
    @ApiModelProperty(value = "主要销售城市")
    private String cityName;
}

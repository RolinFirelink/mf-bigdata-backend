package com.arg.smart.web.product.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 产品生产表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("产品生产表请求参数")
public class ReqMaterialProduce {
    @ApiModelProperty(value = "产品名称")
    private String name;
}

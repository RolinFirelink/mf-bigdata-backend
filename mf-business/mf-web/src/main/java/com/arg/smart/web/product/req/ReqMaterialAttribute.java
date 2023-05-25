package com.arg.smart.web.product.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 产品属性表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("产品属性表请求参数")
public class ReqMaterialAttribute {
    @ApiModelProperty(value = "属性字段")
    private String attributeField;

    @ApiModelProperty(value = "属性名")
    private String attributeName;

    @ApiModelProperty(value = "属性值")
    private String attributeValue;
}

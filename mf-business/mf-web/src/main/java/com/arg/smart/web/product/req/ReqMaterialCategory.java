package com.arg.smart.web.product.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 产品类型表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("产品类型表请求参数")
public class ReqMaterialCategory {
    @ApiModelProperty(value = "分类名称")
    private String name;

    @ApiModelProperty(value = "分类编号")
    private String serialNo;

    @ApiModelProperty(value = "分类等级")
    private Integer level;
}

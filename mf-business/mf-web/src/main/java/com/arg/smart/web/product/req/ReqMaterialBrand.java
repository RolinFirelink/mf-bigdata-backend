package com.arg.smart.web.product.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 产品品牌表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("产品品牌表请求参数")
public class ReqMaterialBrand {
    @ApiModelProperty(value = "品牌名称")
    private String name;

    @ApiModelProperty(value = "品牌归属公司名称")
    private String companyName;


}

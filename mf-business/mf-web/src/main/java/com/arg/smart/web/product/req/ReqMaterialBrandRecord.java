package com.arg.smart.web.product.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 品牌产品中间表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("品牌产品中间表请求参数")
public class ReqMaterialBrandRecord {

    private Long materialId;

    private Long brandId;
}

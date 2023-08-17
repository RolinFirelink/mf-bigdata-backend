package com.arg.smart.web.product.req;

import com.baomidou.mybatisplus.annotation.TableField;
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
    @ApiModelProperty(value = "公司名")
    private String companyName;

    @ApiModelProperty(value = "产品名称")
    private String name;

    @ApiModelProperty(value = "产品类型id")
    private Long categoryId;

    @ApiModelProperty(value = "产品基地名")
    private String productBaseName;

    @ApiModelProperty(value = "产品类别")
    private Integer flag;
}

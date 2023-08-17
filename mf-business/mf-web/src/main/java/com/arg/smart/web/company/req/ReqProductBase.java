package com.arg.smart.web.company.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 产品基地
 * @author lwy
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("产品基地请求参数")
public class ReqProductBase {
    @ApiModelProperty(value = "基地名称")
    private String baseName;

    @ApiModelProperty(value = "认证情况")
    private Integer attestation;

    @ApiModelProperty(value = "种植面积最小值")
    private Integer minArea;

    @ApiModelProperty(value = "种植面积最大值")
    private Integer maxArea;

    @ApiModelProperty(value = "产品分类")
    private Integer max;

    @ApiModelProperty(value = "产品类型")
    private Integer flag;

    @ApiModelProperty(value = "交易主体")
    private Integer transactionSubject;
}

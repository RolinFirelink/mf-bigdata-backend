package com.arg.smart.web.company.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @description: 企业生产信息表
 * @author cgli
 * @date: 2023-07-11
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("企业生产信息表请求参数")
public class ReqProduceInfo {
    @ApiModelProperty(value = "公司名称")
    private String companyName;
    @ApiModelProperty(value = "产品类型")
    private Integer flag;
}

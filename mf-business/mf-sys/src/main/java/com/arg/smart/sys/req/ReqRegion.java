package com.arg.smart.sys.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cgli
 * @description: 行政区域
 * @date: 2023-05-06
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("行政区域请求参数")
public class ReqRegion {

    @ApiModelProperty(value = "父id")
    private String pid;
    @ApiModelProperty(value = "行政区划名称")
    private String name;
    @ApiModelProperty(value = "英文名称")
    private String englishName;
    @ApiModelProperty(value = "行政区划代码")
    private String code;
    @ApiModelProperty(value = "国家ID")
    private String countryId;
    @ApiModelProperty(value = "备注信息")
    private String remarks;

}

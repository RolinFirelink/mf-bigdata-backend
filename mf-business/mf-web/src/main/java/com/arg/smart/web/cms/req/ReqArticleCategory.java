package com.arg.smart.web.cms.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author cgli
 * @description: 文章
 * @date: 2023-05-08
 * @version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("文章请求参数")
public class ReqArticleCategory {
    @ApiModelProperty(value = "分类名称")
    private String name;
}

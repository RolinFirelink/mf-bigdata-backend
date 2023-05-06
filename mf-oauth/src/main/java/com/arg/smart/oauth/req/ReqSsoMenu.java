package com.arg.smart.oauth.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 菜单权限表
 * @author cgli
 * @date: 2022-09-21
 * @Version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("菜单权限表请求参数")
public class ReqSsoMenu {
    @ApiModelProperty(value = "客户端ID")
    private String clientId;
    @ApiModelProperty(value = "菜单名称")
    private String menuName;
    @ApiModelProperty(value = "菜单类型（0目录 1菜单 2按钮）")
    private Integer menuType;
    @ApiModelProperty(value = "菜单状态（1显示 0隐藏）")
    private Integer isVisible;
    @ApiModelProperty(value = "权限标识")
    private String permission;
    @ApiModelProperty("是否返回按钮 true 不返回按钮 false 返回按钮")
    private Boolean noButton = false;
}

package com.arg.smart.oauth.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @Description: 用户信息
 * @author cgli
 * @date: 2022-11-13
 * @Version: V1.0.0
 */
@Data
@Accessors(chain = true)
@ApiModel("用户信息请求参数")
public class ReqSsoUser {
    @ApiModelProperty("客户端ID")
    private String clientId;
    @ApiModelProperty("组织ID")
    private String orgId;
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("昵称--用于显示")
    private String nickname;
    @ApiModelProperty(value = "状态（0正常 1停用）")
    private Integer status;
}

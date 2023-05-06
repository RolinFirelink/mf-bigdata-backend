package com.arg.smart.oauth.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author cgli
 * @date: 2020/3/5 18:23
 */
@Data
@ApiModel("二维码返回结果带图片")
@EqualsAndHashCode(callSuper = true)
public class QRCodeImg extends QRCode {
    @ApiModelProperty("二维码")
    private String img;
}

package com.arg.smart.oauth.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author cgli
 * @date: 2020/3/5 17:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RedisQrCode extends QRCode {
    private String accessToken;
}

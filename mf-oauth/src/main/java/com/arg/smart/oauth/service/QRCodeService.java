package com.arg.smart.oauth.service;

import com.arg.smart.oauth.entity.RedisQrCode;

/**
 * @author cgli
 * @date: 2020/3/5 14:57
 */
public interface QRCodeService {
    void saveQRCode(RedisQrCode qrCode);
    RedisQrCode checkQRCode(String code);
    void updateQRCode(RedisQrCode qrCode);
}

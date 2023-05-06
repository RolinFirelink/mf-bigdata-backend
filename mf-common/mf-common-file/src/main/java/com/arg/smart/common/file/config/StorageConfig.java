package com.arg.smart.common.file.config;

import com.arg.smart.common.file.enums.StorageType;
import com.arg.smart.common.file.handler.AliYunStorage;
import com.arg.smart.common.file.handler.LocalStorage;
import com.arg.smart.common.file.handler.Storage;
import com.arg.smart.common.file.config.properties.StorageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: 缓存配置
 * @author cgli
 * @date: 2023/1/5 16:43
 */
@Configuration
public class StorageConfig {
    private StorageProperties storageProperties;

    @Autowired
    public StorageConfig(StorageProperties storageProperties) {
        this.storageProperties = storageProperties;
    }

    @Bean
    public Storage getStorage() {
        switch (StorageType.getStorageType(storageProperties.getActive())) {
            case ALIYUN:
                StorageProperties.AliYun aliYun = storageProperties.getAliyun();
                return new AliYunStorage(storageProperties.getAddress())
                        .setAccessKeyId(aliYun.getAccessKeyId())
                        .setAccessKeySecret(aliYun.getAccessKeySecret())
                        .setBucketName(aliYun.getBucketName())
                        .setEndpoint(aliYun.getEndpoint());
            default:
                StorageProperties.Local local = storageProperties.getLocal();
                return new LocalStorage(storageProperties.getAddress())
                        .setStoragePath(local.getStoragePath());
        }
    }
}

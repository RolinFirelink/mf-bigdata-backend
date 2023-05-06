package com.arg.smart.common.file.enums;

/**
 * @description: 缓存类型
 * @author cgli
 * @date: 2023/1/5 16:47
 */
public enum StorageType {
    LOCAL("local"),
    /**
     * 阿里云
     */
    ALIYUN("aliYun");
    private String value;

    StorageType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static StorageType getStorageType(String value) {
        for (StorageType type : StorageType.values()) {
            if (type.toString().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return StorageType.LOCAL;
    }
}

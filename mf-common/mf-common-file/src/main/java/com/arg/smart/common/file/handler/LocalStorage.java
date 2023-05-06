package com.arg.smart.common.file.handler;

import com.arg.smart.common.core.exception.MyRuntimeException;
import com.arg.smart.common.file.common.StorageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @description: 本地缓存
 * @author cgli
 * @date: 2023/1/5 15:45
 */
@Slf4j
public class LocalStorage extends AbstractStorage {

    public LocalStorage(String address) {
        super(address);
    }

    /**
     * 文件存储路径
     */
    private String storagePath;

    public LocalStorage setStoragePath(String storagePath) {
        this.storagePath = StorageUtils.formatFilePath(storagePath);
        return this;
    }

    @Override
    public void store(InputStream inputStream, long contentLength, String contentType, String filePath) {
        File file = new File(getFilePath(filePath));
        try {
            FileUtils.copyInputStreamToFile(inputStream, file);
        } catch (IOException e) {
            throw new MyRuntimeException("错误:文件存储异常");
        }
    }

    /**
     * 获取本地路径，补充本地目录信息
     *
     * @param filePath
     * @return
     */
    public String getFilePath(String filePath) {
        return this.storagePath + "/" + filePath;
    }

    @Override
    public Resource loadAsResource(String filePath) {
        try {
            Path file = Paths.get(getFilePath(filePath));
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            return null;
        } catch (MalformedURLException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    @Override
    public void delete(String filePath) {
        Path file = Paths.get(getFilePath(filePath));
        try {
            Files.delete(file);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new MyRuntimeException("错误:文件删除异常");
        }
    }
}
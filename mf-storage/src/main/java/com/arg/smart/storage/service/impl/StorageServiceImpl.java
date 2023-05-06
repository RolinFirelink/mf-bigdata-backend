package com.arg.smart.storage.service.impl;

import com.arg.smart.common.file.entity.StorageInfo;
import com.arg.smart.storage.mapper.StorageMapper;
import com.arg.smart.storage.service.StorageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @description: 文件缓存
 * @author cgli
 * @date: 2023-01-05
 * @version: V1.0.0
 */
@Service
public class StorageServiceImpl extends ServiceImpl<StorageMapper, StorageInfo> implements StorageService {

}

package com.arg.smart.web.cloudMap.service;

import com.arg.smart.web.cloudMap.entity.CloudMap;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 云图
 * @author cgli
 * @date: 2023-08-25
 * @version: V1.0.0
 */
public interface CloudMapService extends IService<CloudMap> {

    List<CloudMap> publicList();
}

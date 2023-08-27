package com.arg.smart.web.cloudMap.service.impl;

import com.arg.smart.web.cloudMap.entity.CloudMap;
import com.arg.smart.web.cloudMap.mapper.CloudMapMapper;
import com.arg.smart.web.cloudMap.service.CloudMapService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 云图
 * @author cgli
 * @date: 2023-08-25
 * @version: V1.0.0
 */
@Service
public class CloudMapServiceImpl extends ServiceImpl<CloudMapMapper, CloudMap> implements CloudMapService {

    @Override
    public List<CloudMap> publicList() {
        LambdaQueryWrapper<CloudMap> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderByAsc(CloudMap::getSort);
        return this.list(queryWrapper);
    }
}

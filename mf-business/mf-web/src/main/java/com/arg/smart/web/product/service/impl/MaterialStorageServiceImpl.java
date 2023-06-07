package com.arg.smart.web.product.service.impl;

import com.arg.smart.web.product.entity.MaterialStorage;
import com.arg.smart.web.product.mapper.MaterialStorageMapper;
import com.arg.smart.web.product.req.ReqMaterialStorage;
import com.arg.smart.web.product.service.MaterialStorageService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @description: 产品库存表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Service
public class MaterialStorageServiceImpl extends ServiceImpl<MaterialStorageMapper, MaterialStorage> implements MaterialStorageService {

    @Override
    public List<MaterialStorage> list(ReqMaterialStorage reqMaterialStorage) {
        LambdaQueryWrapper<MaterialStorage> queryWrapper = new LambdaQueryWrapper<>();
        String materialName = reqMaterialStorage.getMaterialName();
        if(materialName != null){
            queryWrapper.like(MaterialStorage::getMaterialName,materialName);
        }
        return this.list(queryWrapper);
    }
}

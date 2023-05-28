package com.arg.smart.web.product.service.impl;

import com.arg.smart.web.product.entity.Material;
import com.arg.smart.web.product.entity.MaterialCategory;
import com.arg.smart.web.product.mapper.MaterialMapper;
import com.arg.smart.web.product.req.ReqMaterial;
import com.arg.smart.web.product.service.MaterialCategoryService;
import com.arg.smart.web.product.service.MaterialService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 产品表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements MaterialService {

    @Resource
    private MaterialCategoryService materialCategoryService;



    @Override
    public List<Material> getOptions() {
        LambdaQueryWrapper<Material> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(Material::getId,Material::getName);
        return this.list(queryWrapper);
    }

    @Override
    public String getNameById(Long materialId) {
        Material material = this.getById(materialId);
        return material == null ? null : material.getName();
    }

    @Override
    public List<Material> list(ReqMaterial reqMaterial) {
        LambdaQueryWrapper<Material> queryWrapper = new LambdaQueryWrapper<>();
        String name = reqMaterial.getName();
        //产品名称
        if(name != null){
            queryWrapper.like(Material::getName,name);
        }
        //查询产品类别名并返回
        return this.list(queryWrapper).stream().peek(item-> item.setCategoryName(materialCategoryService.getNameById(item.getCategoryId()))).collect(Collectors.toList());
    }

}

package com.arg.smart.web.product.service.impl;

import com.arg.smart.common.core.utils.TreeUtils;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.web.product.entity.MaterialCategory;
import com.arg.smart.web.product.mapper.MaterialCategoryMapper;
import com.arg.smart.web.product.req.ReqMaterialCategory;
import com.arg.smart.web.product.service.MaterialCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 产品类型表
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Service
public class MaterialCategoryServiceImpl extends ServiceImpl<MaterialCategoryMapper, MaterialCategory> implements MaterialCategoryService {

    @Override
    public PageResult<MaterialCategory> listCategory(ReqMaterialCategory reqMaterialCategory) {
        String name = reqMaterialCategory.getName();
        LambdaQueryWrapper<MaterialCategory> queryWrapper = new LambdaQueryWrapper<>();
        if (name != null) {
            queryWrapper.like(MaterialCategory::getName, name);
        }
        // 查出一级分类
        queryWrapper.eq(MaterialCategory::getParentId, 0);
        List<MaterialCategory> list = this.list(queryWrapper);
        PageResult<MaterialCategory> pageResult = new PageResult<>(list);
        // 设置空子数组
        List<MaterialCategory> collect = list.stream().peek(item->{
            item.setChildren(new ArrayList<>());
        }).collect(Collectors.toList());
        return pageResult.setList(collect);
    }

    @Override
    public List<MaterialCategory> listByParentId(Long parentId) {
        LambdaQueryWrapper<MaterialCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialCategory::getParentId, parentId);
        return this.list(queryWrapper).stream().peek(item -> item.setChildren(new ArrayList<>())).collect(Collectors.toList());
    }

    @Override
    public String getNameById(Long categoryId) {
        MaterialCategory materialCategory = this.getById(categoryId);
        if(materialCategory == null){
            return null;
        }
        return materialCategory.getName();
    }
}

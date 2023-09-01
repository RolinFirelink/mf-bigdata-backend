package com.arg.smart.web.product.service.impl;

import co.elastic.clients.elasticsearch.tasks.GroupBy;
import com.arg.smart.common.core.utils.TreeUtils;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.product.entity.MaterialCategory;
import com.arg.smart.web.product.mapper.MaterialCategoryMapper;
import com.arg.smart.web.product.service.MaterialCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    public PageResult<MaterialCategory> listCategory() {
        LambdaQueryWrapper<MaterialCategory> queryWrapper = new LambdaQueryWrapper<>();
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

    @Override
    public PageResult<MaterialCategory> listCategoryByName(String name) {
        LambdaQueryWrapper<MaterialCategory> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(name!= null,MaterialCategory::getName,name);
        List<MaterialCategory> list = this.list(queryWrapper);
        setParent(list);
        List<MaterialCategory> res = new ArrayList<>();
        TreeUtils.buildTree(0L, list, res, MaterialCategory.class);
        return new PageResult<>(res);
    }

    private void setParent(List<MaterialCategory> list){
        //得到当前所有的id作为集合
        Set<Long> ids = list.stream().map(MaterialCategory::getId).collect(Collectors.toSet());
        //得到所有的父ID
        Set<Long> parentIds = list.stream().map(MaterialCategory::getParentId).collect(Collectors.toSet());
        //找出需要查找的ID
        List<Long> collect = parentIds.stream().filter(item -> item != 0L && !ids.contains(item)).collect(Collectors.toList());
        if(collect.size() == 0){
            return;
        }
        List<MaterialCategory> materialCategories = this.listByIds(collect);
        if(materialCategories.size() == 0){
            return;
        }
        list.addAll(materialCategories);
        setParent(list);
    }
}

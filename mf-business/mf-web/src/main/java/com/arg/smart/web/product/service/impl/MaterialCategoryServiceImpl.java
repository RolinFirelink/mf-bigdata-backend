package com.arg.smart.web.product.service.impl;

import com.arg.smart.common.core.utils.TreeUtils;
import com.arg.smart.web.product.entity.MaterialCategory;
import com.arg.smart.web.product.mapper.MaterialCategoryMapper;
import com.arg.smart.web.product.req.ReqMaterialCategory;
import com.arg.smart.web.product.service.MaterialCategoryService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 产品类型表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Service
public class MaterialCategoryServiceImpl extends ServiceImpl<MaterialCategoryMapper, MaterialCategory> implements MaterialCategoryService {

    @Override
    public List<MaterialCategory> listCategory(ReqMaterialCategory reqMaterialCategory) {
        String name = reqMaterialCategory.getName();
        QueryWrapper<MaterialCategory> materialCategoryQueryWrapper = new QueryWrapper<>();
        if(name != null){
            materialCategoryQueryWrapper.like("name",name);
        }
        List<MaterialCategory> list = this.list(materialCategoryQueryWrapper);
        List<MaterialCategory> categoryTree = new ArrayList<>();
        TreeUtils.buildTree(0L,list,categoryTree,MaterialCategory.class);
        return categoryTree;
    }
}

package com.arg.smart.web.product.service.impl;

import com.arg.smart.web.product.entity.MaterialBrand;
import com.arg.smart.web.product.mapper.MaterialBrandMapper;
import com.arg.smart.web.product.service.MaterialBrandService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @author cgli
 * @description: 产品品牌表
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Service
public class MaterialBrandServiceImpl extends ServiceImpl<MaterialBrandMapper, MaterialBrand> implements MaterialBrandService {

    @Override
    public List<MaterialBrand> getOptions() {
        LambdaQueryWrapper<MaterialBrand> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(MaterialBrand::getId, MaterialBrand::getName);
        return this.list(queryWrapper);
    }

    @Override
    public String getNameById(Long brandId) {
        MaterialBrand materialBrand = this.getById(brandId);
        return materialBrand == null ? null : materialBrand.getName();
    }
}

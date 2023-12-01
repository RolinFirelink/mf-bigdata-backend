package com.arg.smart.web.product.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.product.entity.Material;
import com.arg.smart.web.product.entity.MaterialBrand;
import com.arg.smart.web.product.entity.MaterialBrandRecord;
import com.arg.smart.web.product.mapper.MaterialBrandMapper;
import com.arg.smart.web.product.mapper.MaterialBrandRecordMapper;
import com.arg.smart.web.product.req.ReqMaterialBrandRecord;
import com.arg.smart.web.product.service.MaterialBrandRecordService;
import com.arg.smart.web.product.service.MaterialBrandService;
import com.arg.smart.web.product.service.MaterialService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author cgli
 * @description: 品牌产品中间表
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Service
public class MaterialBrandRecordServiceImpl extends ServiceImpl<MaterialBrandRecordMapper, MaterialBrandRecord> implements MaterialBrandRecordService {

    @Resource
    private MaterialService materialService;

    @Resource
    private MaterialBrandService materialBrandService;
    @Resource
    private MaterialBrandMapper materialBrandMapper;

    @Override
    public PageResult<MaterialBrandRecord> list(ReqMaterialBrandRecord reqMaterialBrandRecord) {
        LambdaQueryWrapper<MaterialBrandRecord> queryWrapper = new LambdaQueryWrapper<>();
        if (reqMaterialBrandRecord != null) {
            Long materialId = reqMaterialBrandRecord.getMaterialId();
            if (materialId != null) {
                queryWrapper.eq(MaterialBrandRecord::getMaterialId, materialId);
            }
            Long brandId = reqMaterialBrandRecord.getBrandId();
            if (brandId != null) {
                queryWrapper.eq(MaterialBrandRecord::getBrandId, brandId);
            }
        }
        List<MaterialBrandRecord> list = this.list(queryWrapper);

        PageResult<MaterialBrandRecord> pageResult = new PageResult<>(list);

        if(list.size() == 0){
            return new PageResult<>();
        }

        //获取品牌ID列表
        List<Long> brandIds = list.stream().map(MaterialBrandRecord::getBrandId).collect(Collectors.toList());
        //获取品牌列表
        List<MaterialBrand> materialBrands = materialBrandService.listByIds(brandIds);
        Map<Long, String> materialBrandMap = materialBrands.stream().collect(Collectors.toMap(MaterialBrand::getId, MaterialBrand::getName));

        //获取产品ID列表
        List<Long> materialIds = list.stream().map(MaterialBrandRecord::getMaterialId).collect(Collectors.toList());
        //获取产品列表
        List<Material> materials = materialService.listByIds(materialIds);
        Map<Long, String> materialMap = materials.stream().collect(Collectors.toMap(Material::getId, Material::getName));

        List<MaterialBrandRecord> collect = list.stream().peek(item -> {
            item.setMaterialName(materialMap.get(item.getMaterialId()));
            item.setBrandName(materialBrandMap.get(item.getBrandId()));
        }).collect(Collectors.toList());

        pageResult.setList(collect);

        return pageResult;


    }

    @Override
    public boolean saveMaterialBrandRecord(MaterialBrandRecord materialBrandRecord) {
        return this.save(materialBrandRecord);
    }

    @Override
    public boolean updateMaterialBrandRecord(MaterialBrandRecord materialBrandRecord) {
        return this.updateById(materialBrandRecord);
    }

    @Override
    public List<MaterialBrand> getBrandList(ReqMaterialBrandRecord reqMaterialBrandRecord) {
        Long materialId = reqMaterialBrandRecord.getMaterialId();
        LambdaQueryWrapper<MaterialBrandRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(materialId != null,MaterialBrandRecord::getMaterialId,materialId);
        List<MaterialBrandRecord> list = this.list(queryWrapper);
        if(list.size() == 0){
            return new ArrayList<>();
        }
        List<Long> collect = list.stream().map(MaterialBrandRecord::getBrandId).collect(Collectors.toList());
        return materialBrandService.listByIds(collect);
    }

    @Override
    public List<MaterialBrandRecord> selectListMaterialBrandRecord(Long id) {

        LambdaQueryWrapper<MaterialBrandRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialBrandRecord::getMaterialId, id);
        List<MaterialBrandRecord> materialBrandRecords = baseMapper.selectList(queryWrapper);


        //数据为空时返回空列表
        if (materialBrandRecords.size() == 0) {
            return new ArrayList<>();
        }

        Set<Long> ids = new HashSet<>();
        for (MaterialBrandRecord materialBrandRecord : materialBrandRecords) {
            ids.add(materialBrandRecord.getBrandId());
        }

        //查询品牌信息
        List<MaterialBrand> materialBrands = materialBrandService.listByIds(ids);
        Map<Long, String> idToNameMap = materialBrands.stream()
                .collect(Collectors.toMap(MaterialBrand::getId, MaterialBrand::getName));

        materialBrandRecords.forEach(materialBrandRecord ->
                materialBrandRecord.setBrandName(idToNameMap.get(materialBrandRecord.getBrandId()))
        );

        return materialBrandRecords;
    }
}
package com.arg.smart.web.product.service.impl;

import com.arg.smart.web.product.entity.MaterialBrandRecord;
import com.arg.smart.web.product.mapper.MaterialBrandRecordMapper;
import com.arg.smart.web.product.req.ReqMaterialBrandRecord;
import com.arg.smart.web.product.service.MaterialBrandRecordService;
import com.arg.smart.web.product.service.MaterialBrandService;
import com.arg.smart.web.product.service.MaterialService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;
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

    @Override
    public List<MaterialBrandRecord> list(ReqMaterialBrandRecord reqMaterialBrandRecord) {
        LambdaQueryWrapper<MaterialBrandRecord> queryWrapper = new LambdaQueryWrapper<>();
        if(reqMaterialBrandRecord != null){
            Long materialId = reqMaterialBrandRecord.getMaterialId();
            if(materialId != null){
                queryWrapper.eq(MaterialBrandRecord::getMaterialId,materialId);
            }
            Long brandId = reqMaterialBrandRecord.getBrandId();
            if(brandId != null){
                queryWrapper.eq(MaterialBrandRecord::getBrandId,brandId);
            }
        }
        return this.list(queryWrapper).stream().peek(item -> {

            item.setMaterialName(materialService.getNameById(item.getMaterialId()));
            item.setBrandName(materialBrandService.getNameById(item.getBrandId()));
        }).collect(Collectors.toList());
    }

    @Override
    public boolean saveMaterialBrandRecord(MaterialBrandRecord materialBrandRecord) {
        if(checkDuplicates(materialBrandRecord.getMaterialId(),materialBrandRecord.getBrandId())){
            return false;
        }
        return this.save(materialBrandRecord);
    }

    @Override
    public boolean updateMaterialBrandRecord(MaterialBrandRecord materialBrandRecord) {
        if(checkDuplicates(materialBrandRecord.getMaterialId(),materialBrandRecord.getBrandId())){
            return false;
        }
        return this.updateById(materialBrandRecord);
    }

    public boolean checkDuplicates(Long materialId, Long brandId) {
        LambdaQueryWrapper<MaterialBrandRecord> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(MaterialBrandRecord::getMaterialId, materialId)
                .eq(MaterialBrandRecord::getBrandId, brandId);
        return this.getOne(queryWrapper) != null;
    }
}
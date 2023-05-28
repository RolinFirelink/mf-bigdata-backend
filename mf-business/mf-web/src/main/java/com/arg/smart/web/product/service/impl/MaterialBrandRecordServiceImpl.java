package com.arg.smart.web.product.service.impl;

import com.arg.smart.web.product.entity.MaterialBrandRecord;
import com.arg.smart.web.product.mapper.MaterialBrandRecordMapper;
import com.arg.smart.web.product.req.ReqMaterialBrandRecord;
import com.arg.smart.web.product.service.MaterialBrandRecordService;
import com.arg.smart.web.product.service.MaterialBrandService;
import com.arg.smart.web.product.service.MaterialService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description: 品牌产品中间表
 * @author cgli
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
        return this.list().stream().peek(item->{
            item.setMaterialName(materialService.getNameById(item.getMaterialId()));
            item.setBrandName(materialBrandService.getNameById(item.getBrandId()));
        }).collect(Collectors.toList());
    }
}

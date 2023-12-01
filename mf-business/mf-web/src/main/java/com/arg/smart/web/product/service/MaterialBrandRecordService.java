package com.arg.smart.web.product.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.product.entity.MaterialBrand;
import com.arg.smart.web.product.entity.MaterialBrandRecord;
import com.arg.smart.web.product.req.ReqMaterialBrandRecord;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 品牌产品中间表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
public interface MaterialBrandRecordService extends IService<MaterialBrandRecord> {

    PageResult<MaterialBrandRecord> list(ReqMaterialBrandRecord reqMaterialBrandRecord);

    boolean saveMaterialBrandRecord(MaterialBrandRecord materialBrandRecord);

    boolean updateMaterialBrandRecord(MaterialBrandRecord materialBrandRecord);

    List<MaterialBrand> getBrandList(ReqMaterialBrandRecord reqMaterialBrandRecord);
    List<MaterialBrandRecord> selectListMaterialBrandRecord(Long id);
}

package com.arg.smart.web.product.service;

import com.arg.smart.web.product.entity.MaterialBrand;
import com.arg.smart.web.product.req.ReqMaterialBrand;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 产品品牌表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
public interface MaterialBrandService extends IService<MaterialBrand> {
    List<MaterialBrand> selectListByCondition(ReqMaterialBrand reqMaterialBrand);

    List<MaterialBrand> getOptions();

    String getNameById(Long brandId);

    List<MaterialBrand> list(ReqMaterialBrand reqMaterialBrand);
}

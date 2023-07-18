package com.arg.smart.web.product.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.web.product.entity.MaterialCategory;
import com.arg.smart.web.product.req.ReqMaterialCategory;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 产品类型表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
public interface MaterialCategoryService extends IService<MaterialCategory> {

    PageResult<MaterialCategory> listCategory();

    List<MaterialCategory> listByParentId(Long parentId);

    String getNameById(Long categoryId);

    PageResult<MaterialCategory> listCategoryByName(String name);
}

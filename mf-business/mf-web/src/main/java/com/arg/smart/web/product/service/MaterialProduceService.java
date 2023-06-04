package com.arg.smart.web.product.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.arg.smart.web.product.req.ReqMaterialProduce;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 产品生产表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
public interface MaterialProduceService extends IService<MaterialProduce> {

    PageResult<MaterialProduce> list(ReqMaterialProduce reqMaterialProduce);
}

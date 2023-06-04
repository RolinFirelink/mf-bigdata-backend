package com.arg.smart.web.product.service;

import com.arg.smart.web.product.entity.MaterialStorage;
import com.arg.smart.web.product.req.ReqMaterialStorage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 产品库存表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
public interface MaterialStorageService extends IService<MaterialStorage> {

    List<MaterialStorage> list(ReqMaterialStorage reqMaterialStorage);
}

package com.arg.smart.web.product.service;

import com.arg.smart.web.product.entity.Material;
import com.arg.smart.web.product.req.ReqMaterial;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 产品表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
public interface MaterialService extends IService<Material> {

    List<Material> selectListByCondition(ReqMaterial reqMaterial);

    List<Material> getOptions();

    String getNameById(Long materialId);
}

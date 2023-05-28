package com.arg.smart.web.company.service;

import com.arg.smart.web.company.entity.ProductBase;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 产品基地
 * @author lwy
 * @date: 2023-05-18
 * @version: V1.0.0
 */
public interface ProductBaseService extends IService<ProductBase> {

    List<ProductBase> getOptions();
}

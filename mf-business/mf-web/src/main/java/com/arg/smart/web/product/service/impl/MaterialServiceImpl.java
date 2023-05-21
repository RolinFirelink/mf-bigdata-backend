package com.arg.smart.web.product.service.impl;

import com.arg.smart.web.product.entity.Material;
import com.arg.smart.web.product.mapper.MaterialMapper;
import com.arg.smart.web.product.service.MaterialService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @description: 产品表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
@Service
public class MaterialServiceImpl extends ServiceImpl<MaterialMapper, Material> implements MaterialService {

}

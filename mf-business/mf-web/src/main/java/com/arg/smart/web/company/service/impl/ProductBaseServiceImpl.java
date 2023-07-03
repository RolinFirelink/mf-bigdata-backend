package com.arg.smart.web.company.service.impl;

import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.mapper.ProductBaseMapper;
import com.arg.smart.web.company.req.ReqProductBase;
import com.arg.smart.web.company.service.CompanyService;
import com.arg.smart.web.company.service.ProductBaseService;
import com.arg.smart.web.company.vo.BaseVO;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.arg.smart.web.product.service.MaterialProduceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

/**
 * @author lwy
 * @description: 产品基地
 * @date: 2023-05-18
 * @version: V1.0.0
 */

@Service
public class ProductBaseServiceImpl extends ServiceImpl<ProductBaseMapper, ProductBase> implements ProductBaseService {

    @Resource
    @Lazy
    MaterialProduceService materialProduceService;

    @Override
    public List<ProductBase> getOptions() {
        LambdaQueryWrapper<ProductBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ProductBase::getId, ProductBase::getBaseName);
        return this.list(queryWrapper);
    }

    @Override
    public List<BaseVO> selectListByCondition(ReqProductBase reqProductBase) {
        String baseName = reqProductBase.getBaseName();
        Integer min = reqProductBase.getMin();
        Integer max = reqProductBase.getMax();

        LambdaQueryWrapper<ProductBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(ProductBase::getBaseName, baseName);

        Set<Long> set = new HashSet<>();
        List<ProductBase> productBases = list(queryWrapper);
        for (ProductBase productBase : productBases) {
            set.add(productBase.getCompanyId());
        }

        List<Map<String, Object>> maps = materialProduceService.listMaps(new QueryWrapper<MaterialProduce>()
                .select("company_id,sum(quantity) as sum")
                .groupBy("company_id").in("company_id", set));

        Map<Long, BigDecimal> quantityMap = new HashMap<>();
        for (Map<String, Object> map : maps) {
            quantityMap.put((Long) map.get("company_id"), (BigDecimal) map.get("sum"));
        }
        List<BaseVO> baseVOs = new ArrayList<>();
        for (ProductBase productBase : productBases) {
            BigDecimal scale = quantityMap.get(productBase.getCompanyId());
            BaseVO baseVO = new BaseVO();
            baseVO.setBaseName(productBase.getBaseName());
            baseVO.setScale(scale);
            baseVO.setContacts(productBase.getContacts());
            baseVO.setContactPhone(productBase.getContactPhone());
            baseVO.setAddress(productBase.getAddress());
            baseVOs.add(baseVO);
        }
        List<BaseVO> filteredBaseVOs = baseVOs.stream()
                .filter(baseVO -> baseVO.getScale() != null && baseVO.getScale()
                        .compareTo(BigDecimal.valueOf(min)) > 0 && baseVO.getScale().compareTo(BigDecimal.valueOf(max)) < 0)
                .collect(Collectors.toList());
        System.out.println("filteredBaseVOs = " + filteredBaseVOs);
        return filteredBaseVOs;
    }

    @Override
    public List<ProductBase> list(ReqProductBase reqProductBase) {
        String baseName = reqProductBase.getBaseName();
        LambdaQueryWrapper<ProductBase> queryWrapper = new LambdaQueryWrapper<>();
        if (baseName != null) {
            queryWrapper.like(ProductBase::getBaseName, baseName);
        }
        return this.list(queryWrapper);
    }

}

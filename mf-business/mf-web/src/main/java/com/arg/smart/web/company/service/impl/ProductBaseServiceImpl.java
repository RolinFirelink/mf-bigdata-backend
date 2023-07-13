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
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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

    @Autowired
    CompanyService companyService;
    @Autowired
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

    @Override
    public Map<String, Map<String, Object>> queryyield(Integer flag) {
        String regex = "\\d+";
        Pattern pattern = Pattern.compile(regex);

        Map<String, Map<String, Object>> outputMap = new HashMap<>();
        System.out.println(flag);

        processCity("广州",flag, outputMap, pattern);
        processCity("深圳",flag, outputMap, pattern);
        processCity("珠海",flag, outputMap, pattern);
        processCity("东莞",flag, outputMap, pattern);
        processCity("佛山",flag, outputMap, pattern);
        processCity("惠州",flag, outputMap, pattern);
        processCity("江门",flag, outputMap, pattern);
        processCity("湛江",flag, outputMap, pattern);
        processCity("肇庆",flag, outputMap, pattern);
        processCity("茂名",flag, outputMap, pattern);
        processCity("阳江",flag, outputMap, pattern);
        processCity("清远",flag, outputMap, pattern);
        processCity("韶关",flag, outputMap, pattern);
        processCity("揭阳",flag, outputMap, pattern);
        processCity("汕尾",flag, outputMap, pattern);
        processCity("潮州",flag, outputMap, pattern);
        processCity("河源",flag, outputMap, pattern);
        processCity("云浮",flag, outputMap, pattern);

        return outputMap;
    }

    private void processCity(String cityName, Integer flag, Map<String, Map<String, Object>> outputMap, Pattern pattern) {
        QueryWrapper<ProductBase> wrapper = new QueryWrapper<>();
        wrapper.eq("flag", flag);
        wrapper.like("base_name", cityName);
        List<ProductBase> list = baseMapper.selectList(wrapper);

        int num = 0;
        long totalOutput = 0;

        for (ProductBase productBase : list) {
            if (productBase != null && productBase.getAnnualOutput() != null) {
                Matcher matcher = pattern.matcher(productBase.getAnnualOutput());
                if (matcher.find()) {
                    totalOutput += Long.parseLong(matcher.group());
                    num++;
                }
            }
        }

        Map<String, Object> cityData = new HashMap<>();
        cityData.put("totalOutput", totalOutput);
        cityData.put("num", num);

        outputMap.put(cityName, cityData);
    }
}


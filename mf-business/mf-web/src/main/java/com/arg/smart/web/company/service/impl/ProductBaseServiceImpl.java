package com.arg.smart.web.company.service.impl;

import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.company.mapper.ProductBaseMapper;
import com.arg.smart.web.company.req.ReqProductBase;
import com.arg.smart.web.company.service.ProductBaseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.arg.smart.web.company.vo.BaseVO;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.Map;

/**
 * @author lwy
 * @description: 产品基地
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Service
@Slf4j
public class ProductBaseServiceImpl extends ServiceImpl<ProductBaseMapper, ProductBase> implements ProductBaseService {

    @Override
    public List<ProductBase> getOptions() {
        LambdaQueryWrapper<ProductBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.select(ProductBase::getId, ProductBase::getBaseName);
        return this.list(queryWrapper);
    }

    @Override
    public List<ProductBase> list(ReqProductBase reqProductBase) {
        LambdaQueryWrapper<ProductBase> queryWrapper = new LambdaQueryWrapper<>();
        String baseName = reqProductBase.getBaseName();
        if (baseName != null) {
            queryWrapper.like(ProductBase::getBaseName, baseName);
        }
        Integer maxArea = reqProductBase.getMaxArea();
        if (maxArea != null) {
            queryWrapper.le(ProductBase::getArea, maxArea);
        }
        Integer minArea = reqProductBase.getMinArea();
        if (minArea != null) {
            queryWrapper.ge(ProductBase::getArea, minArea);
        }
        Integer attestation = reqProductBase.getAttestation();
        if (attestation != null) {
            queryWrapper.like(ProductBase::getAttestation, attestation);
        }
        Integer flag = reqProductBase.getFlag();
        if (flag != null) {
            queryWrapper.eq(ProductBase::getFlag, flag);
        }
        queryWrapper.orderByDesc(ProductBase::getWebsiteAddress);
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


package com.arg.smart.web.data.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.data.entity.ProductBaseDayData;
import com.arg.smart.web.data.mapper.ProductBaseDayDataMapper;
import com.arg.smart.web.data.req.ReqProductBaseDayData;
import com.arg.smart.web.data.service.ProductBaseDayDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author cgli
 * @description: 产品基地每日数据
 * @date: 2023-07-18
 * @version: V1.0.0
 */
@Service
public class ProductBaseDayDataServiceImpl extends ServiceImpl<ProductBaseDayDataMapper, ProductBaseDayData> implements ProductBaseDayDataService {

    @Override
    public PageResult<ProductBaseDayData> list(ReqProductBaseDayData reqProductBaseDayData) {
        LambdaQueryWrapper<ProductBaseDayData> queryWrapper = new LambdaQueryWrapper<>();
        String baseName = reqProductBaseDayData.getBaseName();
        Integer flag = reqProductBaseDayData.getFlag();
        Date endTime = reqProductBaseDayData.getEndTime();
        Date startTime = reqProductBaseDayData.getEndTime();
        queryWrapper.like(baseName != null, ProductBaseDayData::getBaseName, baseName)
                .eq(flag != null, ProductBaseDayData::getFlag, flag)
                .between(startTime != null && endTime != null, ProductBaseDayData::getTime, startTime, endTime);
        return new PageResult<>(this.list(queryWrapper));
    }
}

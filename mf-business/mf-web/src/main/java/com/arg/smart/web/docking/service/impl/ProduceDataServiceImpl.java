package com.arg.smart.web.docking.service.impl;

import com.arg.smart.web.docking.entity.MarketPriceData;
import com.arg.smart.web.docking.entity.ProduceData;
import com.arg.smart.web.docking.mapper.ProduceDataMapper;
import com.arg.smart.web.docking.req.ReqProduceData;
import com.arg.smart.web.docking.service.ProduceDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Date;
import java.util.List;

/**
 * @description: 惠农网产量数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Service
public class ProduceDataServiceImpl extends ServiceImpl<ProduceDataMapper, ProduceData> implements ProduceDataService {

    @Override
    public List<ProduceData> list(ReqProduceData reqProduceData) {

        LambdaQueryWrapper<ProduceData> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer flag = reqProduceData.getFlag();
        Integer startYear = reqProduceData.getStartYear();
        Integer endYear = reqProduceData.getEndYear();
        String region = reqProduceData.getRegion();
        lambdaQueryWrapper.eq(flag != null,ProduceData::getFlag,flag);
        lambdaQueryWrapper.ge(startYear != null,ProduceData::getDateYear,startYear);
        lambdaQueryWrapper.le(endYear != null,ProduceData::getDateYear,endYear);
        lambdaQueryWrapper.like(region != null,ProduceData::getStartArea,region);
        return this.list(lambdaQueryWrapper);
    }
}

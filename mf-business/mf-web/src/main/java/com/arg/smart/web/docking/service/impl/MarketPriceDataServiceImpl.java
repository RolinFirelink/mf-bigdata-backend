package com.arg.smart.web.docking.service.impl;


import com.arg.smart.web.docking.entity.MarketPriceData;
import com.arg.smart.web.docking.mapper.MarketPriceDataMapper;
import com.arg.smart.web.docking.req.ReqMarketPriceData;
import com.arg.smart.web.docking.service.MarketPriceDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description: 惠农网批发市场数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Service
public class MarketPriceDataServiceImpl extends ServiceImpl<MarketPriceDataMapper, MarketPriceData> implements MarketPriceDataService {

    @Override
    public List<MarketPriceData> list(ReqMarketPriceData reqMarketPrice) {
        LambdaQueryWrapper<MarketPriceData> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer flag = reqMarketPrice.getFlag();
        Date startDate = reqMarketPrice.getStartDate();
        Date endDate = reqMarketPrice.getEndDate();
        lambdaQueryWrapper.eq(flag != null,MarketPriceData::getFlag,flag);
        lambdaQueryWrapper.ge(startDate != null,MarketPriceData::getDate,startDate);
        lambdaQueryWrapper.le(endDate != null,MarketPriceData::getDate,endDate);
        return this.list(lambdaQueryWrapper);
    }

}

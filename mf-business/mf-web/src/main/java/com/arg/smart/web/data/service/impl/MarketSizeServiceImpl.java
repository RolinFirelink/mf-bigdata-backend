package com.arg.smart.web.data.service.impl;

import com.arg.smart.web.data.entity.MarketSize;
import com.arg.smart.web.data.mapper.MarketSizeMapper;
import com.arg.smart.web.data.req.ReqMarketSize;
import com.arg.smart.web.data.service.MarketSizeService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarketSizeServiceImpl extends ServiceImpl<MarketSizeMapper, MarketSize> implements MarketSizeService {
    @Override
    public List<MarketSize> list(ReqMarketSize reqMarketSize) {
        Integer flag = reqMarketSize.getFlag();
        Integer year = reqMarketSize.getYear();
        LambdaQueryWrapper<MarketSize> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(flag != null,MarketSize::getFlag,flag);
        queryWrapper.eq(year != null,MarketSize::getYear,year);
        return this.list(queryWrapper);
    }
}

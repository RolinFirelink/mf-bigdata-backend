package com.arg.smart.web.product.service.impl;

import com.arg.smart.web.product.entity.MarketPrice;
import com.arg.smart.web.product.mapper.MarketPriceMapper;
import com.arg.smart.web.product.req.ReqMarketPrice;
import com.arg.smart.web.product.service.MarketPriceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author cgli
 * @description: 批发市场价格
 * @date: 2023-07-09
 * @version: V1.0.0
 */
@Service
public class MarketPriceServiceImpl extends ServiceImpl<MarketPriceMapper, MarketPrice> implements MarketPriceService {

    @Override
    public List<MarketPrice> list(ReqMarketPrice reqMarketPrice) {
        LambdaQueryWrapper<MarketPrice> queryWrapper = new LambdaQueryWrapper<>();
        Integer flag = reqMarketPrice.getFlag();
        if (flag != null) {
            queryWrapper.eq(MarketPrice::getFlag, flag);
        }
        Integer newDay = reqMarketPrice.getNewDay();
        // 查询最新日期的
        if (newDay != null && newDay == 1) {
            return baseMapper.lastTimeList(flag);
        }
        Date startTime = reqMarketPrice.getStartTime();
        if (startTime != null) {
            queryWrapper.ge(MarketPrice::getRecordDate, startTime);
        }
        Date endTime = reqMarketPrice.getEndTime();
        if (endTime != null) {
            queryWrapper.le(MarketPrice::getRecordDate, endTime);
        }
        return this.list(queryWrapper);
    }
}

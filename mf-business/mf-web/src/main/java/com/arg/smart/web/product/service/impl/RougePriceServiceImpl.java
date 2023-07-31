package com.arg.smart.web.product.service.impl;

import com.arg.smart.web.product.entity.RougePrice;
import com.arg.smart.web.product.mapper.RougePriceMapper;
import com.arg.smart.web.product.req.ReqRougePrice;
import com.arg.smart.web.product.service.RougePriceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

/**
 * @author cgli
 * @description: 肉鸽价格表
 * @date: 2023-07-31
 * @version: V1.0.0
 */
@Service
public class RougePriceServiceImpl extends ServiceImpl<RougePriceMapper, RougePrice> implements RougePriceService {

    @Override
    public List<RougePrice> queryList(ReqRougePrice reqRougePrice) {
        Integer count = reqRougePrice.getCount();
        String region = reqRougePrice.getRegion();
        String day = reqRougePrice.getDay();
        QueryWrapper<RougePrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("date, region, AVG(price) as price, day, unit")
                .lambda()
                .like(region != null, RougePrice::getRegion, region)
                .like(day != null, RougePrice::getDay, day)
                .groupBy(RougePrice::getDate)
                .groupBy(RougePrice::getDay)
                .groupBy(RougePrice::getUnit)
                .orderByDesc(RougePrice::getDate);
        if (reqRougePrice.getCount() != null) {
            queryWrapper.last("limit " + count);
        }
        return this.list(queryWrapper);
    }

    @Override
    public List<RougePrice> getTrend(ReqRougePrice reqRougePrice) {
        Integer count = reqRougePrice.getCount();
        String region = reqRougePrice.getRegion();
        String day = reqRougePrice.getDay();
        LocalDate startTime = reqRougePrice.getStartTime();
        LocalDate endTime = reqRougePrice.getEndTime();
        if (endTime == null) {
            endTime = LocalDate.now();
        }
        if (startTime == null) {
            startTime = endTime.minusDays(30);
        }
        QueryWrapper<RougePrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("date, AVG(price) as price, day, unit")
                .lambda()
                .like(region != null, RougePrice::getRegion, region)
                .like(day != null, RougePrice::getDay, day)
                .ge(RougePrice::getDate, startTime)
                .le(RougePrice::getDate, endTime)
                .groupBy(RougePrice::getDate)
                .groupBy(RougePrice::getDay)
                .groupBy(RougePrice::getUnit)
                .orderByDesc(RougePrice::getDate);
        if (reqRougePrice.getCount() != null) {
            queryWrapper.last("limit " + count);
        }
        return this.list(queryWrapper);
    }
}

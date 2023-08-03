package com.arg.smart.web.product.service.impl;

import com.arg.smart.web.product.entity.RougePrice;
import com.arg.smart.web.product.entity.vo.RougePriceVo;
import com.arg.smart.web.product.mapper.RougePriceMapper;
import com.arg.smart.web.product.req.ReqRougePrice;
import com.arg.smart.web.product.service.RougePriceService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author cgli
 * @description: 肉鸽价格表
 * @date: 2023-07-31
 * @version: V1.0.0
 */
@Service
public class RougePriceServiceImpl extends ServiceImpl<RougePriceMapper, RougePrice> implements RougePriceService {

    @Override
    public List<RougePriceVo> queryList(ReqRougePrice reqRougePrice) {
        Integer count = reqRougePrice.getCount();
        String region = reqRougePrice.getRegion();
        String day = reqRougePrice.getDay();
        QueryWrapper<RougePrice> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("date, AVG(price) as price, day")
                .lambda()
                .like(region != null, RougePrice::getRegion, region)
                .like(day != null, RougePrice::getDay, day)
                .groupBy(RougePrice::getDate)
                .groupBy(RougePrice::getDay)
                .groupBy(RougePrice::getUnit)
                .orderByDesc(RougePrice::getDate);
        if (reqRougePrice.getCount() != null) {
            if (count <= 0) {
                return null;
            }
            //更新count数量保证查询数量是从大到小的不同时间数量
            count = this.baseMapper.dayNumber(count);
            queryWrapper.last("limit " + count);
        }
        List<RougePrice> list = this.list(queryWrapper);
        return changeType(list);
    }

    @Override
    public List<RougePriceVo> getTrend(ReqRougePrice reqRougePrice) {
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
            if (count <= 0) {
                return null;
            }
            count = this.baseMapper.dayNumber(count);
            queryWrapper.last("limit " + count);
        }
        return changeType(this.list(queryWrapper));
    }

    private List<RougePriceVo> changeType(List<RougePrice> list) {
        List<RougePriceVo> listResult = new ArrayList<>();
        RougePriceVo rougePriceVo = new RougePriceVo();
        if (list.size() > 0) {
            rougePriceVo.setDate(list.get(0).getDate());
        }
        for (RougePrice rougePrice : list) {
            //若已经包含说明进入下一个时间点的存储，new新对象，存储旧对象
            if (rougePriceVo.getDay().containsKey(rougePrice.getDay())) {
                listResult.add(rougePriceVo);
                //创建新对象
                rougePriceVo = new RougePriceVo();
                //存储新时间点
                rougePriceVo.setDate(rougePrice.getDate());
            }
            //不存在则继续存在该对象中即可
            rougePriceVo.getDay().put(rougePrice.getDay(), rougePrice.getPrice());
        }
        //存储最后一个数据
        listResult.add(rougePriceVo);
        return listResult;
    }
}

package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.statistics.entity.BuyersIndex;
import com.arg.smart.web.statistics.entity.ProductionStatistics;
import com.arg.smart.web.statistics.mapper.BuyersIndexMapper;
import com.arg.smart.web.statistics.req.ReqBuyersIndex;
import com.arg.smart.web.statistics.service.BuyersIndexService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @description: 采购商指数
 * @author cgli
 * @date: 2023-07-16
 * @version: V1.0.0
 */
@Service
public class BuyersIndexServiceImpl extends ServiceImpl<BuyersIndexMapper, BuyersIndex> implements BuyersIndexService {

    @Override
    public List<BuyersIndex> list(ReqBuyersIndex reqBuyersIndex) {
        QueryWrapper<BuyersIndex> queryWrapper = new QueryWrapper<>();
        Date startTime = reqBuyersIndex.getStartTime();
        Date endTime = reqBuyersIndex.getEndTime();
        queryWrapper.ge(startTime != null ,"statistical_time",startTime)
            .le(endTime != null,"statistical_time",endTime);
        Integer flag = reqBuyersIndex.getFlag();
        queryWrapper.eq("flag",flag);
        queryWrapper.orderByDesc("year").orderByDesc("month");
        return this.list(queryWrapper);
    }

    @Override
    public PageResult<BuyersIndex> listPage(ReqBuyersIndex reqBuyersIndex) {
        LambdaQueryWrapper<BuyersIndex> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(reqBuyersIndex.getFlag()!=null, BuyersIndex::getFlag, reqBuyersIndex.getFlag())
                .eq(reqBuyersIndex.getYear()!=null, BuyersIndex::getYear, reqBuyersIndex.getYear())
                .eq(reqBuyersIndex.getMonth()!=null, BuyersIndex::getMonth, reqBuyersIndex.getMonth());
        return new PageResult<>(this.list(queryWrapper));
    }
}

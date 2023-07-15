package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.web.statistics.entity.ProductionStatistics;
import com.arg.smart.web.statistics.entity.ProvinceSaleStatistics;
import com.arg.smart.web.statistics.mapper.ProvinceSaleStatisticsMapper;
import com.arg.smart.web.product.req.ReqProvinceSaleStatistics;
import com.arg.smart.web.statistics.service.ProvinceSaleStatisticsService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 省份销售数据
 * @author cgli
 * @date: 2023-07-15
 * @version: V1.0.0
 */
@Service
public class ProvinceSaleStatisticsServiceImpl extends ServiceImpl<ProvinceSaleStatisticsMapper, ProvinceSaleStatistics> implements ProvinceSaleStatisticsService {

    @Override
    public List<ProvinceSaleStatistics> list(ReqProvinceSaleStatistics reqProvinceSaleStatistics) {
        Integer flag = reqProvinceSaleStatistics.getFlag();
        //昨天的日期
        LocalDate time = LocalDate.now().minusDays(1);
        //查出广东省最新统计记录的
        QueryWrapper<ProvinceSaleStatistics> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("flag",flag).eq("province","广东").
                eq("statistical_time", time);
        ProvinceSaleStatistics guangdongData = this.getOne(queryWrapper);
        Integer count = reqProvinceSaleStatistics.getCount();
        List<ProvinceSaleStatistics> list = new ArrayList<>();
        list.add(guangdongData);
        // 查出四个最突出的省份数据，默认4个
        if(count == null ){
            count = 4;
        }
        queryWrapper = new QueryWrapper<>();
        queryWrapper.ne("province","广东").eq("flag",flag).eq("statistical_time",time).groupBy("province");
        Integer searchType = reqProvinceSaleStatistics.getSearchType();
        if(searchType == null || searchType == 0){
            // 按销售量排行
            queryWrapper.select("province","max(sales) sales","min(average_price) average_price").orderByDesc("sales");
        }else{
            queryWrapper.select("province","min(average_price) average_price","max(sales) sales").orderByAsc("average_price");
        }
        queryWrapper.last("limit "+count);
        list.addAll(this.list(queryWrapper));
        return list;
    }
}

package com.arg.smart.web.statistics.service.impl;

import com.arg.smart.sys.api.remote.RemoteDictService;
import com.arg.smart.web.company.service.CompanyService;
import com.arg.smart.web.company.service.ProductBaseService;
import com.arg.smart.web.statistics.entity.vo.StatisticsData;
import com.arg.smart.web.statistics.service.StatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class StatisticsServiceImpl implements StatisticsService {


    @Resource
    private RemoteDictService remoteDictService;
    @Resource
    private CompanyService companyService;

    @Resource
    private ProductBaseService productBaseService;

    @Override
    public List<StatisticsData> getStatisticsData() {
        //获取产品类型种数
        Integer productCount = remoteDictService.getProductCount("getStatisticsData");
        //获取企业数量
        Integer companyCount = companyService.getCount();
        //获取产地数量
        Integer productBaseCount = productBaseService.getCount();
        List<StatisticsData> statisticsDataList = new ArrayList<>();
        statisticsDataList.add(new StatisticsData("产品种类",productCount));
        statisticsDataList.add(new StatisticsData("企业数量",companyCount));
        statisticsDataList.add(new StatisticsData("产地数量",productBaseCount));
        return statisticsDataList;
    }
}

package com.arg.smart.web.statistics.service;

import com.arg.smart.web.statistics.entity.vo.StatisticsData;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface StatisticsService {
    List<StatisticsData> getStatisticsData();
}

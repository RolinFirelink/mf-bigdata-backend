package com.arg.smart.web.statistics.controller;

import com.arg.smart.common.core.utils.AuthInfoUtils;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.common.core.web.ReqPage;
import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.statistics.entity.PurchasingHeat;
import com.arg.smart.web.statistics.entity.vo.StatisticsData;
import com.arg.smart.web.statistics.req.ReqPurchasingHeat;
import com.arg.smart.web.statistics.service.StatisticsService;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @description: 统计数据接口
 * @author zsj
 * @date: 2023-11-15
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "采购热度")
@RestController
@RequestMapping("/statistics")
public class StatisticsController {

    @Resource
    private StatisticsService statisticsService;

    /**
     * 获取后台首页统计数据
     *
     * @return 后台首页统计数据
     */
    @ApiOperation(value = "获取后台首页统计数据", notes = "获取后台首页统计数据")
    @GetMapping
    public Result<List<StatisticsData>> getStatisticsData(HttpServletRequest request) {
        return Result.ok(statisticsService.getStatisticsData(), "后台首页统计数据-查询成功!");
    }
}

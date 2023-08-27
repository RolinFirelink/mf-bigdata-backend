package com.arg.smart.web.report.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.report.entity.GoodAnalyse;
import com.arg.smart.web.report.req.ReqGoodAnalyse;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * @Author GlowingTree
 * @Description 农产品竞品分析报告接口
 * @Date 8/24/2023 2:10 PM
 * @Version 1.0
 */
public interface GoodAnalyseService extends IService<GoodAnalyse> {
    /**
     * 获取农产品竞品分析报告
     * @param firstGood 第一种产品
     * @param secondGood 第二种产品
     * @param year 年份
     * @param month 月份
     * @return Map<String, Object>
     */
    Map<String, Object> getGoodAnalyseReport(String firstGood, String secondGood, Integer year, Integer month);

    /**
     * 农产品竞品分析报告分页列表查询
     * @param reqGoodAnalyse 农产品竞品分析报告条件查询类
     * @return PageResult<GoodAnalyse>
     */
    PageResult<GoodAnalyse> list(ReqGoodAnalyse reqGoodAnalyse);
}

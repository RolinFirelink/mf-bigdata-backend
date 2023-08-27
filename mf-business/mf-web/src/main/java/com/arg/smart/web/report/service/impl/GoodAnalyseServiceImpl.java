package com.arg.smart.web.report.service.impl;

import com.arg.smart.common.core.utils.StringUtils;
import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.order.model.ModuleFlag;
import com.arg.smart.web.report.entity.GoodAnalyse;
import com.arg.smart.web.report.entity.ReportMapper;
import com.arg.smart.web.report.entity.vo.GoodAnalyseVO;
import com.arg.smart.web.report.mapper.GoodAnalyseMapper;
import com.arg.smart.web.report.report_handler.good_analyse.GoodAnalyseHandler;
import com.arg.smart.web.report.req.ReqGoodAnalyse;
import com.arg.smart.web.report.service.GoodAnalyseService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @Author GlowingTree
 * @Description 农产品竞品分析服务实现类
 * @Date 27/08/2023 21:13
 * @Version 1.0
 */
@Service
@Slf4j
public class GoodAnalyseServiceImpl extends ServiceImpl<GoodAnalyseMapper, GoodAnalyse> implements GoodAnalyseService {

    @Resource
    private Environment env;
    @Resource
    private GoodAnalyseHandler goodAnalyseHandler;

    @Override
    public Map<String, Object> getGoodAnalyseReport(String firstGood, String secondGood, Integer year, Integer month) {

        StringBuilder pattern = new StringBuilder();

        ReportMapper reportMapper = new ReportMapper(env);
        reportMapper.initMapper();

        ModuleFlag.initFlagNameMap();

        // 生成报告标题, 作者信息, 正文模板
        pattern.append(env.getProperty("reports.title.goods-analyse"));
        pattern.append(env.getProperty("reports.source"));
        pattern.append(env.getProperty("reports.content.goods-analyse"));
        // 模板替换
        HashMap<String, Object> map = new HashMap<>();
        int flag = goodAnalyseHandler.checkGoodInModule(firstGood, secondGood);
        if (flag < ModuleFlag.CHICKEN || flag > ModuleFlag.PREFABRICATED_DISHES) {
            map.put("report", String.format("待比较的产品 %s 与 %s 不属于同一个模块, 无法比较", firstGood, secondGood));
            return map;
        }
        // 属于同一模块, 可以比较
        String patternStr = pattern.toString(), retStr = patternStr;
        // 爬取网页数据
        GoodAnalyseVO firstGoodAnalyseInfo = goodAnalyseHandler.getGoodInfoFromWeb(firstGood),
                secondGoodAnalyseInfo = goodAnalyseHandler.getGoodInfoFromWeb(secondGood);

        retStr = retStr.replace("{first_good_name}", firstGood);
        retStr = retStr.replace("{second_good_name}", secondGood);
        // 获取产品图片
        if (firstGoodAnalyseInfo != null && StringUtils.isNotEmpty(firstGoodAnalyseInfo.getPicUrl())) {
            retStr = retStr.replace("{first_good_pic}", firstGoodAnalyseInfo.getPicUrl());
        }
        if (secondGoodAnalyseInfo != null && StringUtils.isNotEmpty(secondGoodAnalyseInfo.getPicUrl())) {
            retStr = retStr.replace("{second_good_pic}", secondGoodAnalyseInfo.getPicUrl());
        }
        // 获取产品简介
        if (firstGoodAnalyseInfo != null && StringUtils.isNotEmpty(firstGoodAnalyseInfo.getSummary())) {
            retStr = retStr.replace("{first_good_summary}", firstGoodAnalyseInfo.getSummary());
        }
        if (secondGoodAnalyseInfo != null && StringUtils.isNotEmpty(secondGoodAnalyseInfo.getSummary())) {
            retStr = retStr.replace("{second_good_summary}", secondGoodAnalyseInfo.getSummary());
        }
        // 获取产品详情
        if (firstGoodAnalyseInfo != null && StringUtils.isNotEmpty(firstGoodAnalyseInfo.getFeature())) {
            retStr = retStr.replace("{first_good_feat}", firstGoodAnalyseInfo.getFeature());
        }
        if (secondGoodAnalyseInfo != null && StringUtils.isNotEmpty(secondGoodAnalyseInfo.getFeature())) {
            retStr = retStr.replace("{second_good_feat}", secondGoodAnalyseInfo.getFeature());
        }
        // 获取产品供需情况
        retStr = retStr.replace("{first_supply_and_demand}", goodAnalyseHandler.getGoodAnalyseSupplyAndDemandInfo(flag, firstGood, year, month));
        retStr = retStr.replace("{second_supply_and_demand}", goodAnalyseHandler.getGoodAnalyseSupplyAndDemandInfo(flag, secondGood, year, month));
        // 获取产品消费者评价
        retStr = retStr.replace("{first_customer_evaluation}", goodAnalyseHandler.getProductCustomerEvaluation(flag, firstGood, year, month));
        retStr = retStr.replace("{second_customer_evaluation}", goodAnalyseHandler.getProductCustomerEvaluation(flag, secondGood, year, month));

        // 如用到 flag, 则替换 flag
        retStr = retStr.replaceAll("\\{flag}", ModuleFlag.FLAG_NAME.get(flag));

        for (Map.Entry<String, Supplier<String>> entry : reportMapper.getMapper().entrySet()) {
            retStr = retStr.replaceAll(entry.getKey(), entry.getValue().get());
        }

        map.put("report", retStr);
        map.put("flag", flag);
        return map;
    }

    @Override
    public PageResult<GoodAnalyse> list(ReqGoodAnalyse reqGoodAnalyse) {
        LambdaQueryWrapper<GoodAnalyse> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(reqGoodAnalyse.getId() != null, GoodAnalyse::getId, reqGoodAnalyse.getId());
        wrapper.eq(reqGoodAnalyse.getFirstGood() != null, GoodAnalyse::getFirstGood, reqGoodAnalyse.getFirstGood());
        wrapper.eq(reqGoodAnalyse.getSecondGood() != null, GoodAnalyse::getSecondGood, reqGoodAnalyse.getSecondGood());
        wrapper.eq(reqGoodAnalyse.getYear() != null, GoodAnalyse::getYear, reqGoodAnalyse.getYear());
        wrapper.eq(reqGoodAnalyse.getMonth() != null, GoodAnalyse::getMonth, reqGoodAnalyse.getMonth());
        wrapper.ge(reqGoodAnalyse.getStartTime() != null, GoodAnalyse::getReportTime, reqGoodAnalyse.getStartTime());
        wrapper.lt(reqGoodAnalyse.getEndTime() != null, GoodAnalyse::getReportTime, reqGoodAnalyse.getEndTime());
        return new PageResult<>(this.list(wrapper));
    }

}

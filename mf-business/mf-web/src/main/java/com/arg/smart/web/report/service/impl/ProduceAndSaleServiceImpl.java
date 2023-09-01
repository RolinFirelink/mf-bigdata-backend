package com.arg.smart.web.report.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.order.model.ModuleFlag;
import com.arg.smart.web.report.entity.ProduceAndSale;
import com.arg.smart.web.report.entity.ReportMapper;
import com.arg.smart.web.report.mapper.ProductAndSaleMapper;
import com.arg.smart.web.report.req.ReqProduceAndSale;
import com.arg.smart.web.report.service.ProduceAndSaleService;
import com.arg.smart.web.report.service.ReportKeyHandler;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @Author GlowingTree
 * @Description 农产品分析报告实现类
 * @Date 8/24/2023 2:12 PM
 * @Version 1.0
 */
@Service
@Slf4j
public class ProduceAndSaleServiceImpl extends ServiceImpl<ProductAndSaleMapper, ProduceAndSale> implements ProduceAndSaleService {

    @Resource
    private Environment env;
    @Resource
    private List<ReportKeyHandler> keyHandlers;

    @Override
    public String getProductAndSaleReport(Integer flag, Integer year, Integer month) {

        StringBuilder pattern = new StringBuilder();

        ReportMapper reportMapper = new ReportMapper(env, year, month);
        reportMapper.initMapper();

        ModuleFlag.initFlagNameMap();

        // 生成报告标题与作者
        pattern.append(env.getProperty("reports.title.production-and-sale"));
        pattern.append(env.getProperty("reports.source"));
        // 生成报告正文内容
        pattern.append(env.getProperty("reports.content.production-and-sale"));

        String patternStr = pattern.toString(), retStr = patternStr;
        if (patternStr.isEmpty()) {
            return "";
        }

        // 处理报告内容, 使用 ReportKeyHandlers 中每一个 Handler 进行处理
        for (ReportKeyHandler handler : keyHandlers) {
            retStr = retStr.replaceAll(handler.getKey(), handler.handle(flag, year, month));
        }
        // 处理报告内容, 使用 Mapper 中设置进行处理
        for (Map.Entry<String, Supplier<String>> entry : reportMapper.getMapper().entrySet()) {
            retStr = retStr.replaceAll(entry.getKey(), entry.getValue().get());
        }

        retStr = retStr.replaceAll("\\{flag}", ModuleFlag.FLAG_NAME.get(flag));

        return retStr;
    }

    @Override
    public PageResult<ProduceAndSale> list(ReqProduceAndSale reqProduceAndSale) {
        LambdaQueryWrapper<ProduceAndSale> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(reqProduceAndSale.getId() != null, ProduceAndSale::getId, reqProduceAndSale.getId());
        wrapper.eq(reqProduceAndSale.getYear() != null, ProduceAndSale::getYear, reqProduceAndSale.getYear());
        wrapper.eq(reqProduceAndSale.getMonth() != null, ProduceAndSale::getMonth, reqProduceAndSale.getMonth());
        wrapper.ge(reqProduceAndSale.getStartTime() != null, ProduceAndSale::getReportTime, reqProduceAndSale.getStartTime());
        wrapper.lt(reqProduceAndSale.getEndTime() != null, ProduceAndSale::getReportTime, reqProduceAndSale.getEndTime());
        return new PageResult<>(this.list(wrapper));
    }

}

package com.arg.smart.web.company.uitls;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.company.entity.Company;
import com.arg.smart.web.company.entity.vo.CompanyExcel;
import com.arg.smart.web.company.service.CompanyService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CompanyDataListener extends AnalysisEventListener<CompanyExcel> {

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    private final CompanyService companyService;

    public CompanyDataListener(CompanyService companyService) {
        this.companyService = companyService;
    }

    List<Company> list = new ArrayList<>();

    //读取数据会执行这方法
    @Override
    public void invoke(CompanyExcel companyExcel, AnalysisContext analysisContext) {
        log.info(companyExcel.toString());
        Company company = new Company();
        BeanUtils.copyProperties(companyExcel, company);
        list.add(company);
        if (list.size() >= BATCH_COUNT) {
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        companyService.saveBatch(list);
        log.info("所有数据导入完成");
    }
}

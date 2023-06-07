package com.arg.smart.web.company.uitls;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.arg.smart.web.company.entity.Company;
import com.arg.smart.web.company.entity.vo.CompanyExcel;
import com.arg.smart.web.company.service.CompanyService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

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
        String id = companyExcel.getId();
        if (id != null) {
            company.setId(Long.valueOf(id));
        }
        company.setCompanyName(companyExcel.getCompanyName());
        company.setCompanyNo(companyExcel.getCompanyNo());
        company.setJuridicalPerson(companyExcel.getJuridicalPerson());
        company.setJuridicalPhone(companyExcel.getJuridicalPhone());
        company.setContacts(companyExcel.getContacts());
        company.setContactPhone(companyExcel.getContactPhone());
        company.setEmail(companyExcel.getEmail());
        company.setAreaName(companyExcel.getAreaName());
        company.setAreaCode(companyExcel.getAreaCode());
        company.setAddress(companyExcel.getAddress());
        String companyType = companyExcel.getCompanyType();
        if(companyType != null){
            company.setCompanyType(Integer.valueOf(companyType));
        }
        String productType = companyExcel.getProductType();
        if(productType != null){
            company.setProductType(Integer.valueOf(productType));
        }
        String enabled = companyExcel.getEnabled();
        if(enabled != null){
            company.setEnabled(Integer.valueOf(companyExcel.getEnabled()));
        }
        company.setRemark(companyExcel.getRemark());
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

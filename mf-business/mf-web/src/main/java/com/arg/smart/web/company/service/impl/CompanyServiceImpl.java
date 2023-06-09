package com.arg.smart.web.company.service.impl;

import com.arg.smart.web.company.entity.Company;
import com.arg.smart.web.company.mapper.CompanyMapper;
import com.arg.smart.web.company.req.ReqCompany;
import com.arg.smart.web.company.service.CompanyService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @author lbz
 * @description: 企业、供货商、销售商和承运商
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Slf4j
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Override
    public List<Company> SelectListByCondition(ReqCompany reqCompany) {
        if(reqCompany == null){
            return this.list();
        }
        QueryWrapper<Company> companyQueryWrapper = new QueryWrapper<>();
        Integer companyType = reqCompany.getCompanyType();
        String companyName = reqCompany.getCompanyName();
        if(companyType != null && companyType != 0){
            companyQueryWrapper.eq("company_type",companyType);
        }
        if(companyName != null){
            companyQueryWrapper.like("company_name",companyName);
        }
        return this.list(companyQueryWrapper);
    }

    @Override
    public List<Company> getOptionsByCompanyType(Integer companyType) {

        LambdaQueryWrapper<Company> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Company::getCompanyType,companyType)
                .select(Company::getId,Company::getCompanyName);
        return this.list(queryWrapper);

    }

}

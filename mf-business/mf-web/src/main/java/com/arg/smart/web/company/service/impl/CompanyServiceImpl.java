package com.arg.smart.web.company.service.impl;

import com.arg.smart.web.company.entity.Company;
import com.arg.smart.web.company.mapper.CompanyMapper;
import com.arg.smart.web.company.req.ReqCompany;
import com.arg.smart.web.company.service.CompanyService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
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
    public List<Company> SelectListByCompanyType(ReqCompany reqCompany) {
        if(reqCompany == null){
            return this.list();
        }
        Integer companyType = reqCompany.getCompanyType();
        Integer productType = reqCompany.getProductType();
        QueryWrapper<Company> companyQueryWrapper = new QueryWrapper<>();
        if(companyType != null){
            companyQueryWrapper.eq("company_type",companyType);
        }
        if(productType != null){
            companyQueryWrapper.eq("product_type",productType);
        }
        return this.list(companyQueryWrapper);
    }
}

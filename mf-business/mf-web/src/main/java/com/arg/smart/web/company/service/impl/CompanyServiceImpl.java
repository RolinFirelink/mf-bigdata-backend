package com.arg.smart.web.company.service.impl;

import com.arg.smart.web.company.entity.Company;
import com.arg.smart.web.company.mapper.CompanyMapper;
import com.arg.smart.web.company.service.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import javax.annotation.Resource;
import java.util.List;

/**
 * @description: 企业、供货商、销售商和承运商
 * @author lbz
 * @date: 2023-05-18
 * @version: V1.0.0
 */
@Slf4j
@Service
public class CompanyServiceImpl extends ServiceImpl<CompanyMapper, Company> implements CompanyService {

    @Resource
    private CompanyMapper companyMapper;

    @Override
    public List<Company> SelectListByCompanyType(int companyType) {
        List<Company> companies = companyMapper.selectListByCompanyType(companyType);
        log.info("companyType:"+companyType);
        return companies;
    }
}
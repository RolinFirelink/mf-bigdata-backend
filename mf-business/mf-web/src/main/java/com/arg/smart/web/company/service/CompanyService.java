package com.arg.smart.web.company.service;

import com.arg.smart.web.company.entity.Company;
import com.arg.smart.web.company.req.ReqCompany;
import com.arg.smart.web.company.vo.CompanyVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @description: 企业、供货商、销售商和承运商
 * @author lbz
 * @date: 2023-05-18
 * @version: V1.0.0
 */
public interface CompanyService extends IService<Company> {


    List<Company>  list(ReqCompany reqCompany);

    List<Company> getOptionsByCompanyType(Integer companyType);

    Map<String, List<CompanyVO>>getCompanyVOByCity(Integer flag, String cityName);

    boolean saveCompany(Company company);

    boolean updateCompanyById(Company company);

    Integer getCount();
}

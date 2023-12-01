package com.arg.smart.web.data.service;


import com.arg.smart.web.data.entity.CompanyRevenue;
import com.arg.smart.web.data.req.ReqCompanyRevenue;
import com.baomidou.mybatisplus.extension.service.IService;
import java.util.List;

public interface CompanyRevenueService extends IService<CompanyRevenue> {
    List<CompanyRevenue> selectCompanyRevenue(Integer year,Integer count);
    List<CompanyRevenue> list(ReqCompanyRevenue reqCompanyRevenue);
}


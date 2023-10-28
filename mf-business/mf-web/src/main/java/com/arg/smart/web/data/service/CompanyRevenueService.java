package com.arg.smart.web.data.service;

import com.arg.smart.web.data.entity.CompanyProduct;
import com.arg.smart.web.data.entity.CompanyRevenue;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CompanyRevenueService extends IService<CompanyRevenue> {
    List<CompanyRevenue> selectCompanyRevenue(Integer year);
}

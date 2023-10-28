package com.arg.smart.web.data.service.impl;

import com.arg.smart.web.data.entity.CompanyRevenue;
import com.arg.smart.web.data.mapper.CompanyRevenueMapper;
import com.arg.smart.web.data.service.CompanyProductService;
import com.arg.smart.web.data.service.CompanyRevenueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

public class CompanyRevenueServiceImpl extends ServiceImpl<CompanyRevenueMapper, CompanyRevenue> implements CompanyRevenueService {
    @Override
    public List<CompanyRevenue> selectCompanyRevenue(Integer year) {
        return baseMapper.selectCompanyRevenues(year);
    }
}

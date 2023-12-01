package com.arg.smart.web.data.service.impl;

import com.arg.smart.web.data.entity.CompanyRevenue;
import com.arg.smart.web.data.mapper.CompanyRevenueMapper;
import com.arg.smart.web.data.service.CompanyRevenueService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import java.util.List;
import com.arg.smart.web.data.req.ReqCompanyRevenue;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;



@Service

public class CompanyRevenueServiceImpl extends ServiceImpl<CompanyRevenueMapper, CompanyRevenue> implements CompanyRevenueService {
    @Override
    public List<CompanyRevenue> selectCompanyRevenue(Integer year,Integer count) {
        if(count == null){
            count = 4;
        }
        if(year == null){
            year = 2022;
        }
        return baseMapper.selectCompanyRevenues(year,count);
    }
    @Override
    public List<CompanyRevenue> list(ReqCompanyRevenue reqCompanyRevenue) {

        String companyName = reqCompanyRevenue.getCompanyName();
        Integer flag = reqCompanyRevenue.getFlag();
        Integer year = reqCompanyRevenue.getYear();
        LambdaQueryWrapper<CompanyRevenue> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(flag != null,CompanyRevenue::getFlag,flag);
        queryWrapper.like(companyName != null,CompanyRevenue::getCompanyName,companyName);
        queryWrapper.eq(year != null,CompanyRevenue::getYear,year);
        return this.list(queryWrapper);
    }
}

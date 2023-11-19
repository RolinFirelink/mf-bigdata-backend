package com.arg.smart.web.data.mapper;

import com.arg.smart.web.data.entity.CompanyRevenue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CompanyRevenueMapper extends BaseMapper<CompanyRevenue> {
    @Select("SELECT id,flag,company_name,company_revenue FROM sh_company_revenue WHERE YEAR(date) = #{year}")
    List<CompanyRevenue> selectCompanyRevenues(@Param("year") Integer year);
}


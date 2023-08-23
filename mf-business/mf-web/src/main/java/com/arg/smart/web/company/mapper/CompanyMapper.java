package com.arg.smart.web.company.mapper;

import com.arg.smart.web.company.entity.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface CompanyMapper extends BaseMapper<Company> {

    @Select("select company_name from sh_company where id = #{id}")
    String getNameById(@Param("id") Long companyId);

    @Select("select pids_name from mf_system.sys_region where id = #{areaCode}")
    String getPidsName(String areaCode);
}

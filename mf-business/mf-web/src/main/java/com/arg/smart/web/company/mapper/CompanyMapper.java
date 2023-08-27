package com.arg.smart.web.company.mapper;

import com.arg.smart.web.company.entity.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface CompanyMapper extends BaseMapper<Company> {

    @Select("select company_name from sh_company where id = #{id}")
    String getNameById(@Param("id") Long companyId);

    @Select("select pids_name from mf_system.sys_region where id = #{areaCode}")
    String getPidsName(String areaCode);

    @Update("update sh_company set city = null where id = #{id}")
    void updateCity(Long id);

    @Update("update sh_company set region = null where id = #{id}")
    void updateRegion(Long id);

    @Update("update sh_company set province = null where id = #{id}")
    void updateProvince(Long id);
}

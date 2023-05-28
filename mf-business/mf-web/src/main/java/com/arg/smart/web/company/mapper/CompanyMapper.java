package com.arg.smart.web.company.mapper;

import com.arg.smart.web.company.entity.Company;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description: 企业、供货商、销售商和承运商
 * @author lbz
 * @date: 2023-05-18
 * @version: V1.0.0
 */
public interface CompanyMapper extends BaseMapper<Company> {

    //根据企业类型查询企业信息
    List<Company> selectListByCompanyType(@Param("companyType") int companyType);

    //根据产品类型和企业类型查询企业信息
    List<Company> selectListByProductTypeAndCompanyType(@Param("productType") int product_type,@Param("companyType") int companyType);

    @Select("select company_name from sh_company where id = #{companyId}")
    String getNameById(@Param("companyId") Long companyId);
}

package com.arg.smart.web.data.mapper;

import com.arg.smart.web.data.entity.CompanyProduct;
import com.arg.smart.web.data.entity.vo.AvgProductValue;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface CompanyProductMapper extends BaseMapper<CompanyProduct> {
    @Select("SELECT company_name, product_name, AVG(product_value) as avgValue    \n" +
            "FROM sh_company_product    \n" +
            "WHERE product_name = #{productName}    \n" +
            "GROUP BY company_name, product_name;")
    List<AvgProductValue> companyProductValue(@Param("productName") String productName);
}

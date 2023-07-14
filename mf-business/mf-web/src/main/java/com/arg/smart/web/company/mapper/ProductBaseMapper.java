package com.arg.smart.web.company.mapper;

import com.arg.smart.web.company.entity.ProductBase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductBaseMapper extends BaseMapper<ProductBase> {

    @Select("select base_name from sh_product_base where id = #{id}")
    String getNameById(@Param("id") Long baseId);

    @Select("select " +
            "distinct company_id " +
            "from sh_product_base " +
            "where address like CONCAT('%', #{cityName}, '%')"+
            " and flag = flag")
    List<String>getCompanyId(@Param("cityName")String cityName ,@Param("id")Integer flag);
}

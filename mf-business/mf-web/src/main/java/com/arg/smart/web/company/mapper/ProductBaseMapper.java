package com.arg.smart.web.company.mapper;

import com.arg.smart.web.company.entity.ProductBase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ProductBaseMapper extends BaseMapper<ProductBase> {

    @Select("select company_name from sh_product_base where id = #{id}")
    String getNameById(@Param("id") Long baseId);
}

package com.arg.smart.web.company.mapper;

import com.arg.smart.web.company.entity.ProductBase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
<<<<<<< HEAD
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

=======

import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import org.apache.ibatis.annotations.Select;

import java.util.List;


>>>>>>> 67b29d8a2b48656ad20c4945eb1a8732f8df0432
public interface ProductBaseMapper extends BaseMapper<ProductBase> {

    @Select("select base_name from sh_product_base where id = #{id}")
    String getNameById(@Param("id") Long baseId);
}

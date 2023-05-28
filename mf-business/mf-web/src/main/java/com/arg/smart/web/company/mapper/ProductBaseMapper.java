package com.arg.smart.web.company.mapper;

import com.arg.smart.web.company.entity.ProductBase;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @description: 产品基地
 * @author lwy
 * @date: 2023-05-18
 * @version: V1.0.0
 */
public interface ProductBaseMapper extends BaseMapper<ProductBase> {

    @Select("select company_name from sh_product_base where id = #{id}")
    String getNameById(@Param("id") Long baseId);
}

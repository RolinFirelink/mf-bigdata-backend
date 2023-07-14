package com.arg.smart.web.company.mapper;

import com.arg.smart.web.company.entity.ProductInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface ProductInfoMapper extends BaseMapper<ProductInfo> {

    @Select("select base_name from sh_product_base where id = #{id}")
    String getNameById(@Param("id") Long baseId);

    @Select("select contact_phone from sh_product_base where id = #{id}")
    String getIphoneNumberById(@Param("id") Long baseId);

    @Select("select area_code from sh_product_base where id = #{id}")
    Long getAreaCodeById(@Param("id") Long baseId);

    @Select("select lat from mf_system.sys_region where code = #{code}")
    String getlatByareaCode(@Param("code") Long code);

    @Select("select lng from mf_system.sys_region where code = #{code}")
    String getlngByareaCode(@Param("code") Long code);
}

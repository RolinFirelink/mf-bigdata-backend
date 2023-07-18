package com.arg.smart.web.company.mapper;

import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.customer.entity.counter.OccupationCounter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductBaseMapper extends BaseMapper<ProductBase> {

    @Select("select base_name from sh_product_base where id = #{id}")
    String getNameById(@Param("id") Long baseId);

}

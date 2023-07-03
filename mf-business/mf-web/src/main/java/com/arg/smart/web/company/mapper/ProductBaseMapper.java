package com.arg.smart.web.company.mapper;

import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.customer.entity.counter.OccupationCounter;
import com.arg.smart.web.product.entity.baseflag.BaseFlag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
<<<<<<< HEAD
import org.apache.commons.math3.stat.descriptive.summary.Product;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
=======
import org.apache.ibatis.annotations.Select;

import java.util.List;
>>>>>>> 58c88111450b25884623ab7ab42a853f12f707e3

public interface ProductBaseMapper extends BaseMapper<ProductBase> {

    @Select("select base_name from sh_product_base where id = #{id}")
    String getNameById(@Param("id") Long baseId);
}

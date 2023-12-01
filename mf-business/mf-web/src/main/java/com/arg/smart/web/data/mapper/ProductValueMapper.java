package com.arg.smart.web.data.mapper;

import com.arg.smart.web.data.entity.ProductValue;
import com.arg.smart.web.data.entity.SalesFlow;
import com.arg.smart.web.data.entity.vo.ProductValueTotal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ProductValueMapper extends BaseMapper<ProductValue> {
    @Select("SELECT productWeightTotal FROM ( SELECT flag, city, SUM(product_weight) as productWeightTotal FROM sh_product_value WHERE flag = #{flag} AND YEAR(date) = #{year} GROUP BY city ORDER BY productWeightTotal DESC LIMIT 7) AS subquery")
    List<ProductValueTotal> selectProductValueTotal(@Param("flag")Integer flag,@Param("date")Integer date);
}

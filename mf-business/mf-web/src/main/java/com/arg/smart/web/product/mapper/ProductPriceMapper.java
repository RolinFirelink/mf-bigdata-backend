package com.arg.smart.web.product.mapper;

import com.arg.smart.web.product.entity.vo.ProductPrice;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description: 产品价格表
 * @author 韩岳宏
 * @date: 2023-07-12
 * @version: V1.0.0
 */

public interface ProductPriceMapper extends BaseMapper<ProductPrice> {
    @Select("SELECT time,product,avg(price) AS acgPrice ,unit FROM sh_product_price Group By time")
    List<ProductPrice> selectList(@Param(Constants.WRAPPER) Wrapper<ProductPrice> wrapper);
}

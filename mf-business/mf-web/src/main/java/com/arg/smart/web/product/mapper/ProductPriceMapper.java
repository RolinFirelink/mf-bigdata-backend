package com.arg.smart.web.product.mapper;

import com.arg.smart.web.product.entity.vo.ProductPrice;
import com.arg.smart.web.product.entity.vo.ProductSupply;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Select;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.List;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-01
 * @version: V1.0.0
 */
public interface ProductPriceMapper extends BaseMapper<com.arg.smart.web.product.entity.ProductPrice> {



/**
 * @description: 产品价格表
 * @author 韩岳宏
 * @date: 2023-07-12
 * @version: V1.0.0
 */
   /* @Select("SELECT time,product,avg(price) AS acgPrice ,unit FROM sh_product_price Group By time")
    List<ProductPrice> selectList(@Param(Constants.WRAPPER) Wrapper<ProductPrice> wrapper);*/
     @Select("SELECT product as productName,supply as supplyNumber,demand as demandNumber From sh_product_supply_demand_statistics WHERE flag = #{flag}")
     List<ProductSupply> selectSupplyByFlag(@Param("flag") Integer flag);
}

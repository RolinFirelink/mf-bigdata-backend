package com.arg.smart.web.product.mapper;

import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.entity.vo.ProductPriceVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-01
 * @version: V1.0.0
 */
public interface ProductPriceMapper extends BaseMapper<ProductPrice> {

    @Select("select flag,max(time) as time,avg(price) as price,unit ,avg(lifting) as lifting from sh_product_price group by flag,unit")
    List<ProductPrice> getMaxTimePrice();

    @Select("select region from sh_product_price_region")
    List<String> regionList();

    @Select("select max(price) as maxPrice,min(price) as minPrice,time from sh_product_price where flag = #{flag} and time >= #{startTime} and time <= #{endTime} GROUP BY time order by time desc")
    List<ProductPriceVO> publicTrend(
            @Param("flag") Integer flag,@Param("startTime") LocalDate startTime,@Param("endTime") LocalDate endTime);

    @Select("select max(price) as maxPrice" +
            ",min(price) as minPrice " +
            "from sh_product_price " +
            "where flag = #{flag} " +
            "and time = #{startTime} " +
            "and region like concat('%',#{region},'%')")
    ProductPriceVO getDailyPriceInfo(@Param("flag") Integer flag,@Param("startTime") LocalDate startTime, @Param("region")String region);
}

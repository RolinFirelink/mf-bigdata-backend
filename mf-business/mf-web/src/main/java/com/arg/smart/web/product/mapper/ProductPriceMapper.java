package com.arg.smart.web.product.mapper;

import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.entity.vo.PriceTemp;
import com.arg.smart.web.product.entity.vo.ProductPriceVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * @description: 产品价格表
 * @author cgli
 * @date: 2023-07-01
 * @version: V1.0.0
 */
public interface ProductPriceMapper extends BaseMapper<ProductPrice> {

    @Select("select region from sh_product_price_region")
    List<String> regionList();

    @Select("select avg(price) as avgPrice, max(price) as maxPrice,min(price) as minPrice,time " +
            "from sh_product_price " +
            "where flag = #{flag} " +
            "and time >= #{startTime} and time <= #{endTime} " +
            "and region like concat('%',#{region},'%') " +
            "GROUP BY time order by time desc")
    List<ProductPriceVO> publicTrend(
            @Param("flag") Integer flag,
            @Param("startTime") LocalDate startTime,
            @Param("endTime") LocalDate endTime,
            @Param("region") String region);

    @Select("select max(price) as maxPrice" +
            ",min(price) as minPrice " +
            "from sh_product_price " +
            "where flag = #{flag} " +
            "and time = #{startTime} " +
            "and region like concat('%',#{region},'%')")
    ProductPriceVO getDailyPriceInfo(@Param("flag") Integer flag,@Param("startTime") LocalDate startTime, @Param("region")String region);

    //查询每种的最新两天的平均价格
    @Select({
            "<script>",
            "<foreach collection='flags' item='flag' separator=' UNION '>",
            "SELECT price,time,flag FROM(",
            "SELECT AVG(price) as price, time, flag",
            "FROM sh_product_price",
            "WHERE flag = #{flag}",
            "and region like concat('%',#{region},'%')",
            "GROUP BY time, flag",
            "ORDER BY time DESC",
            "LIMIT 2) AS subQuery",
            "</foreach>",
            "ORDER BY flag",
            "</script>"
    })
    List<ProductPrice> getPublicTemp(@Param("flags") List<Integer> flags,@Param("region") String region);

}

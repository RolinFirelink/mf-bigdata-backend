package com.arg.smart.web.product.mapper;

import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.entity.RougePrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


/**
 * @author cgli
 * @description: 肉鸽价格表
 * @date: 2023-07-31
 * @version: V1.0.0
 */
public interface RougePriceMapper extends BaseMapper<RougePrice> {

    @Select("select " +
            "count(1) " +
            "from sh_rouge_price " +
            "where date in " +
            "(select date from " +
            "(select DISTINCT date " +
            "from sh_rouge_price " +
            "order by date DESC limit #{count}) rouge)")
    Integer dayNumber(Integer count);

    @Select({
            "<script>",
            "SELECT AVG(price) as price, date time",
            "FROM sh_rouge_price",
            "where region like concat('%',#{region},'%')",
            "GROUP BY date",
            "ORDER BY date DESC",
            "LIMIT 2",
            "</script>"
    })
    List<ProductPrice> getPriceTempData(@Param("region")String region);

//    List<RougePriceTrendVo> trend(@Param("count")Integer count, @Param("day")String day, @Param("startTime")LocalDate startTime, @Param("endTime")LocalDate endTime);
}

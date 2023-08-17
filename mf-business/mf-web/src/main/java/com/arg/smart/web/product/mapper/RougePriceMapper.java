package com.arg.smart.web.product.mapper;

import com.arg.smart.web.product.entity.RougePrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;


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

//    List<RougePriceTrendVo> trend(@Param("count")Integer count, @Param("day")String day, @Param("startTime")LocalDate startTime, @Param("endTime")LocalDate endTime);
}

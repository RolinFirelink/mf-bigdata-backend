package com.arg.smart.web.product.mapper;

import com.arg.smart.web.product.entity.MarketPrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description: 批发市场价格
 * @author cgli
 * @date: 2023-07-09
 * @version: V1.0.0
 */
public interface MarketPriceMapper extends BaseMapper<MarketPrice> {

    @Select("select * from market_price where ")
    List<MarketPrice> lastTimeList(@Param("flag") Integer flag);
}

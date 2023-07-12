package com.arg.smart.web.product.mapper;

import com.alibaba.fastjson.JSONObject;
import com.arg.smart.web.product.entity.ProductPrice;
import com.arg.smart.web.product.entity.vo.AvgPriceVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import netscape.javascript.JSObject;
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
    /**
     * 按日查询产品的平均价格
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Select("SELECT \n" +
            "\tflag,\n" +
            "\ttime,\n" +
            "\tAVG(price) as price\n" +
            "FROM sh_product_price\n" +
            "WHERE time > #{startTime} AND time < #{endTime}\n" +
            "GROUP BY flag,time\n" +
            "ORDER BY time"
    )
    List<AvgPriceVO> selectAvgPriceOfDate(@Param("startTime")String startTime, @Param("endTime")String endTime);


    /**
     * 按月查询产品的平均价格(季度）
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Select("SELECT \n" +
            "\tflag,\n" +
            "\tCONCAT(YEAR(time),'-', CEIL(MONTH(time)/3)) as time,\n" +
            "\tAVG(price) as price\n" +
            "FROM sh_product_price\n" +
            "WHERE time > #{startTime} AND time < #{endTime}\n" +
            "GROUP BY flag,time\n" +
            "ORDER BY time")
    List<AvgPriceVO> selectAvgPriceOfMonth(@Param("startTime")String startTime, @Param("endTime")String endTime);

//    SELECT
//            flag,
//    CONCAT(YEAR(time),'-', CEIL(MONTH(time)/3)) as season,
//    AVG(price) as avg_price
//    FROM sh_product_price
//    WHERE time < now()
//    GROUP BY flag,time
//    ORDER BY season
}

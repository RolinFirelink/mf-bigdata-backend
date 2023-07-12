package com.arg.smart.web.product.mapper;

import com.arg.smart.web.product.entity.ProductPriceMonth;
import com.arg.smart.web.product.entity.vo.AvgPriceVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ProductPriceMonthMapper extends BaseMapper<ProductPriceMonth> {
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


    /**
     * 按月查询产品的平均价格（半年）
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Select("SELECT \n" +
            "\tflag,\n" +
            "\tDATE_FORMAT(time,\"%Y-%m\") as time,\n" +
            "\tAVG(price) as price\n" +
            "FROM sh_product_price\n" +
            "WHERE time > #{startTime} AND time < #{endTime}\n" +
            "GROUP BY flag,time\n" +
            "ORDER BY time"
    )
    List<AvgPriceVO> selectAvgPriceOfHalfYear(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 按月查询产品的平均价格（年）
     * @param startTime 开始时间
     * @param endTime   结束时间
     * @return
     */
    @Select("SELECT \n" +
            "\tflag,\n" +
            "\tYEAR(time) as time,\n" +
            "\tAVG(price) as price\n" +
            "FROM sh_product_price\n" +
            "WHERE time > #{startTime} AND time < #{endTime}\n" +
            "GROUP BY flag,time\n" +
            "ORDER BY time"
    )
    List<AvgPriceVO> selectAvgPriceOfYear(@Param("startTime")String startTime, @Param("endTime")String endTime);


}

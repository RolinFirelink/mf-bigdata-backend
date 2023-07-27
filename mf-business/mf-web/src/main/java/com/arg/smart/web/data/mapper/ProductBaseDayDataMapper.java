package com.arg.smart.web.data.mapper;


import com.arg.smart.web.data.entity.ProductBaseDayData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.sql.Date;
import java.util.List;

/**
 * @description: 产品基地每日数据
 * @author cgli
 * @date: 2023-07-18
 * @version: V1.0.0
 */
public interface ProductBaseDayDataMapper extends BaseMapper<ProductBaseDayData> {

    @Select("select base_name,product,supply,unit,lat,lng from sh_product_base_day_data where flag = #{flag} " +
            "and time between #{startTime} and #{endTime}")
    List<ProductBaseDayData> getSupplyHeat(@Param("flag") int flag, @Param("startTime") Date startTime,
                                       @Param("endTime") Date endTime);

    @Select("select " +
            "base_name " +
            "from sh_product_base " +
            "where id = #{baseId}")
    String getBaseName(Long baseId);
}

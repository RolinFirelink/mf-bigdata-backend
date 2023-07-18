package com.arg.smart.web.position.mapper;



import com.arg.smart.web.position.entity.PositionData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PositionMapper extends BaseMapper<PositionData> {

    //查看产地经纬度
    @Select("select '产地' AS location,lat,lng from sh_product_base where flag = #{flag}")
    List<PositionData> positionOfOrigin(@Param("flag")Integer flag);

    @Select("select '批发市场' AS location,lat,lng from sh_product_market where flag = #{flag}")
    List<PositionData> positionOfMarket(@Param("flag")Integer flag);

    @Select("select '销售地' AS location,lat,lng from sh_place_of_sale where flag = #{flag}")
    List<PositionData> positionOfSalePlace(@Param("flag")Integer flag);
}
 
package com.arg.smart.web.position.mapper;



import com.arg.smart.web.position.entity.PositionData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionMapper extends BaseMapper<PositionData> {

    //查看产地经纬度
    @Select("select lat,lng from mf_system.sys_region AS sr JOIN mf_market.sh_product_base AS pb ON pb.area_code = sr.code where flag = #{flag}")
    List<PositionData> positionOfOrigin(@Param("flag")Integer flag);
}
 
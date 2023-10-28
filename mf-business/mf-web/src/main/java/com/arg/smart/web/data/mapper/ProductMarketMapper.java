package com.arg.smart.web.data.mapper;

import com.arg.smart.web.data.entity.ProductMarket;
import com.arg.smart.web.position.entity.PositionData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @description: 批发市场表
 * @author cgli
 * @date: 2023-08-18
 * @version: V1.0.0
 */
public interface ProductMarketMapper extends BaseMapper<ProductMarket> {

    @Select("select pids_name from mf_system.sys_region where id = #{areaCode}")
    String getPidName(String areaCode);

    @Select("select lat, lng from mf_system.sys_region where id = #{areaCode}")
    PositionData getLatAndLng(String areaCode);
}

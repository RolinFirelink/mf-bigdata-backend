package com.arg.smart.web.data.mapper;

import com.arg.smart.web.data.entity.PlaceOfSale;
import com.arg.smart.web.position.entity.PositionData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @description: 销售地表
 * @author cgli
 * @date: 2023-08-18
 * @version: V1.0.0
 */
public interface PlaceOfSaleMapper extends BaseMapper<PlaceOfSale> {

    @Select("select pids_name from mf_system.sys_region where id = #{areaCode}")
    String getPidName(String areaCode);

    @Select("select lat,lng from mf_system.sys_region where id = #{areaCode}")
    PositionData getLatAndLng(String areaCode);
}

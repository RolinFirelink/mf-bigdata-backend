package com.arg.smart.web.data.mapper;

import com.arg.smart.web.data.entity.SalesFlow;
import com.arg.smart.web.position.entity.PositionData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

/**
 * @description: 销售流向
 * @author cgli
 * @date: 2023-07-18
 * @version: V1.0.0
 */
public interface SalesFlowMapper extends BaseMapper<SalesFlow> {

    @Select("select pids_name from mf_system.sys_region where id = #{areaCode}")
    String getAddress(String areaCode);

    @Select("select " +
            "lat," +
            "lng " +
            "from mf_system.sys_region " +
            "where id = #{areaCode}")
    PositionData getLatAndLng(String areaCode);
}

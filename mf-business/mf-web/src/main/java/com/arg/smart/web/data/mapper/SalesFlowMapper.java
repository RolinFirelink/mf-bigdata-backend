package com.arg.smart.web.data.mapper;

import com.arg.smart.web.data.entity.SalesFlow;
import com.arg.smart.web.data.entity.vo.SalesFlowUtils;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @description: 销售流向
 * @author cgli
 * @date: 2023-07-18
 * @version: V1.0.0
 */
public interface SalesFlowMapper extends BaseMapper<SalesFlow> {
    @Select("SELECT lat, lng  FROM mf_system.sys_region WHERE code = #{AreaCode}")
    SalesFlowUtils selectByAreaCode(@Param("AreaCode") String AreaCode);
}

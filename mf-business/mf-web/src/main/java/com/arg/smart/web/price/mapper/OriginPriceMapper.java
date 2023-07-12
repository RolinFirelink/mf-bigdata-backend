package com.arg.smart.web.price.mapper;

import com.arg.smart.web.price.entity.OriginPrice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description: 产地价格
 * @author cgli
 * @date: 2023-07-10
 * @version: V1.0.0
 */
public interface OriginPriceMapper extends BaseMapper<OriginPrice> {

    @Select("select " +
            "id " +
            "from sh_company " +
            "where company_name like CONCAT('%', #{companyName}, '%')")
    List<Long> selectCompanyId(String companyName);

    @Select("select " +
            "id " +
            "from sys_region " +
            "where pids_name like CONCAT('%', #{cityName}, '%')")
    List<Long> selectCityCode(String cityName);

    @Select("select " +
            "pids_name " +
            "from mf_system.sys_region " +
            "where id = #{cityCode}")
    String selectMainCity(String cityCode);

}

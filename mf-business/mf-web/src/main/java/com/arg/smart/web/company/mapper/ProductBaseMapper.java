package com.arg.smart.web.company.mapper;

import com.arg.smart.web.company.entity.ProductBase;
import com.arg.smart.web.position.entity.PositionData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.math.BigDecimal;
import java.util.List;

public interface ProductBaseMapper extends BaseMapper<ProductBase> {

    @Select("select base_name from sh_product_base where id = #{id}")
    String getNameById(@Param("id") Long baseId);

    @Select("select " +
            "distinct company_id " +
            "from sh_product_base " +
            "where address like CONCAT('%', #{cityName}, '%')"+
            " and flag = flag")
    List<String>getCompanyId(@Param("cityName")String cityName ,@Param("id")Integer flag);

    @Select("select lat from mf_system.sys_region where code = #{code}")
    long getLatitudeByAreaCode(@Param("code") Long code);

    @Select("select lng from mf_system.sys_region where code = #{code}")
    long getLongitudeByAreaCode(@Param("code") Long code);

    @Select("SELECT DISTINCT SUBSTRING_INDEX(pids_name, '.', 4) AS filtered_pids_name "  +
            "FROM mf_system.sys_region " +
            "WHERE pids_name LIKE CONCAT('%', #{city}, '%')")
    List<String>getDistrict(@Param("city") String city);

    @Select("select " +
            "company_name " +
            "from sh_company " +
            "where id = #{comapnyId}")
    String getCompanyName(Long companyId);

    @Select("select " +
            "lat," +
            "lng " +
            "from mf_system.sys_region " +
            "where id = #{areaCode}")
    PositionData queryLatAndLng(String areaCode);

    @Select("select " +
            "pids_name " +
            "from mf_system.sys_region " +
            "where id = #{areaCode}")
    String queryAddr(String areaCode);

    @Update("update sh_product_base set city = null where id = #{id}")
    void updateCity(Long id);

    @Update("update sh_product_base set region = null where id = #{id}")
    void updateRegion(Long id);

    @Select("select count(*) from sh_product_base where delete_flag = 0")
    Integer getCount();
}

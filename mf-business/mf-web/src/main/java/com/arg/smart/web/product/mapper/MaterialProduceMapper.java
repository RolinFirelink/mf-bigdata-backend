package com.arg.smart.web.product.mapper;

import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.arg.smart.web.product.entity.report.CityWithScale;
import com.arg.smart.web.product.entity.report.MaterialProduceWithCity;
import com.arg.smart.web.product.entity.report.MaterialProduceWithProduceBase;
import com.arg.smart.web.product.entity.report.MaterialProduceWithYear;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author cgli
 * @description: 产品生产表
 * @date: 2023-05-21
 * @version: V1.0.0
 */
public interface MaterialProduceMapper extends BaseMapper<MaterialProduce> {

    @Select("select " +
            "sum(production_scale) as area," +
            "sum(market_estimate) as yield," +
            "YEAR(time_estimate) as year " +
            "from sh_material_produce " +
            "where flag = #{flag} group by YEAR(time_estimate)")
    List<MaterialProduceWithYear> getMaterialProductWithYears(@Param("flag") Integer flag);

    @Select("select " +
            "sum(production_scale) as area," +
            "sum(market_estimate) as yield," +
            "base_id from sh_material_produce " +
            "where flag = #{flag} group by base_id")
    List<MaterialProduceWithProduceBase> getMaterialProduceWithProduceBase(@Param("flag") Integer flag);

    @Select("select " +
            "area," +
            "yield," +
            "base_id," +
            "produce_base_name " +
            "from sh_material_produce_with_produce_base " +
            "where flag = #{flag} AND " +
            "time > DATE_SUB((select MAX(time) from sh_material_produce_with_produce_base), INTERVAL 30 MINUTE) " +
            "group by base_id")
    List<MaterialProduceWithProduceBase> getByProduceIdAndFlag(@Param("flag") Integer flag);

    @Select("select " +
            "sum(production_scale) as area," +
            "sum(quantity) as yield," +
            "base_id," +
            "flag from sh_material_produce " +
            "group by base_id,flag")
    List<MaterialProduceWithProduceBase> selectProduce();

    @Insert("insert into sh_material_produce_with_produce_base " +
            "(area, yield, base_id, produce_base_name, flag) " +
            "VALUES (#{area}, #{yield}, #{baseId}, #{produceBaseName}, #{flag})")
    void insertStatisticalResults(MaterialProduceWithProduceBase insertData);

    @Select("select " +
            "city," +
            "production_scale " +
            "from sh_material_produce_with_city " +
            "where flag = #{flag} AND " +
            "time > DATE_SUB((select MAX(time) from sh_material_produce_with_city), INTERVAL 30 MINUTE) " +
            "group by city")
    List<CityWithScale> queryByCity(Integer flag);

    @Select("select " +
            "MAX(production_scale) as `max`," +
            "MIN(production_scale) as `min`," +
            "unit " +
            "from sh_material_produce_with_city " +
            "where flag = #{flag} AND " +
            "time > DATE_SUB((select MAX(time) from sh_material_produce_with_city), INTERVAL 30 MINUTE)" +
            "group by flag")
    MaterialProduceWithCity queryOneByFlag(Integer flag);

    @Select("select " +
            "SUM(a.production_scale) productionScale," +
            "SUBSTRING(b.address, 4, 3) city," +
            "flag " +
            "from sh_material_produce a,sh_product_base b " +
            "where a.base_id = b.id " +
            "group by a.flag, SUBSTRING(b.address, 4, 3)")
    List<CityWithScale> selectScale();

    @Select("select " +
            "unit " +
            "from sh_material_produce " +
            "where flag = #{flag} " +
            "group by flag")
    String getUnit(Integer flag);

    @Insert("insert into sh_material_produce_with_city " +
            "(unit,city,production_scale,flag) " +
            "VALUES (#{unit}, #{cityWithScale.city}, #{cityWithScale.productionScale}, #{cityWithScale.flag})")
    void insertStatisticalTable(@Param("unit")String unit, @Param("cityWithScale")CityWithScale cityWithScale);
}

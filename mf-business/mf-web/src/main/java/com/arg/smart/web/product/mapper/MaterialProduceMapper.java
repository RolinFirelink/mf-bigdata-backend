package com.arg.smart.web.product.mapper;

import com.arg.smart.web.product.entity.MaterialProduce;
import com.arg.smart.web.product.entity.report.*;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.Date;
import java.util.List;

/**
 * @author cgli
 * @description: 产品生产表
 * @date: 2023-05-21
 * @version: V1.0.0
 */
public interface MaterialProduceMapper extends BaseMapper<MaterialProduce> {
    //select * from table where create_time = max(create_time) and flag = ? group by base_id
    @Select("SELECT * FROM sh_material_produce WHERE flag = ? group by base_id having time_estimate = max(time_estimate)  ")
    List<MaterialProduce> groupByCreatTime(Integer flag);


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
            "any_value(production_scale) production_scale" +
            "from sh_material_produce_with_city " +
            "where flag = #{flag} AND " +
            "time > DATE_SUB((select MAX(time) from sh_material_produce_with_city), INTERVAL 30 MINUTE) " +
            "group by city")
    List<CityWithScale> queryByCity(Integer flag);

    @Select("select " +
            "MAX(production_scale) as `max`," +
            "MIN(production_scale) as `min`," +
            "any_value(unit) unit " +
            "from sh_material_produce_with_city " +
            "where flag = #{flag} AND " +
            "time > DATE_SUB((select MAX(time) from sh_material_produce_with_city), INTERVAL 30 MINUTE)" +
            "group by flag")
    MaterialProduceWithCity queryOneByFlag(Integer flag);

    @Select("select " +
            "SUM(a.production_scale) productionScale," +
            "SUBSTRING(b.address, 4, 3) city," +
            "a.flag " +
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
    void insertStatisticalTable(@Param("unit") String unit, @Param("cityWithScale") CityWithScale cityWithScale);

    @Select("select SUM(market_estimate) AS marketEstimate," +
            "substring(time_estimate,1,7) timeEstimate," +
            "name " +
            "from sh_material_produce " +
            "where flag = #{flag} AND " +
            "time_estimate between #{startTime} AND #{endTime} " +
            "group by substring(time_estimate,1,7), name")
    List<EstimateTimeAndMarket> selectByTime(@Param("flag") Integer flag, @Param("startTime") Date startTime, @Param("endTime") Date endTime);

    @Select("select " +
            "SUM(quantity) quantity, " +
            "name " +
            "from sh_material_produce " +
            "where flag = #{flag} " +
            "group by name")
    List<ProduceNameAndQuantity> getProduceQuantity(Integer flag);

    @Select("select " +
            "name," +
            "SUM(product_estimate) productEstimate " +
            "from sh_material_produce " +
            "where flag = #{flag} " +
            "group by name")
    List<EstimateTimeAndMarket> getUnitQuantity(Integer flag);

    @Select("select " +
            "SUM(production_scale) AS area," +
            "SUM(quantity) AS yield," +
            "YEAR(time_estimate) AS year," +
            "MONTH(time_estimate) AS month " +
            "from sh_material_produce " +
            "where flag = #{flag} " +
            "group by SUBSTRING(time_estimate, 1, 7)")
    List<MaterialProduceWithYear> produceScaleWithMonth(Integer flag);
}

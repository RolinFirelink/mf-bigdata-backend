package com.arg.smart.web.product.mapper;

import com.arg.smart.web.product.entity.MaterialProduce;
import com.arg.smart.web.product.entity.report.MaterialProduceWithProduceBase;
import com.arg.smart.web.product.entity.report.MaterialProduceWithYear;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description: 产品生产表
 * @author cgli
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
            "sum(production_scale) as area,"+
            "sum(market_estimate) as yield,"+
            "base_id from sh_material_produce "+
            "where flag = #{flag} group by base_id")
    List<MaterialProduceWithProduceBase> getMaterialProduceWithProduceBase(@Param("flag") Integer flag);
}

package com.arg.smart.web.data.mapper;

import com.arg.smart.web.data.entity.BaseProductProductionScale;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description: 基地产品生产规模数据表
 * @author cgli
 * @date: 2023-07-20
 * @version: V1.0.0
 */
public interface BaseProductProductionScaleMapper extends BaseMapper<BaseProductProductionScale> {

    @Select("select " +
            "id " +
            "from sh_product_base " +
            "where base_name like CONCAT('%', #{baseName}, '%')")
    List<Long> selectBaseId(@Param("baseName") String baseName);

    @Select("select base_name from sh_product_base where id = #{baseId}")
    String getNameById(@Param("baseId") Long baseId);
}

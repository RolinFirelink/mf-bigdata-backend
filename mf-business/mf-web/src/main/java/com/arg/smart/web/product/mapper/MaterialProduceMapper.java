package com.arg.smart.web.product.mapper;

import com.arg.smart.web.customer.entity.counter.OccupationCounter;
import com.arg.smart.web.product.entity.MaterialProduce;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description: 产品生产表
 * @author cgli
 * @date: 2023-05-21
 * @version: V1.0.0
 */
public interface MaterialProduceMapper extends BaseMapper<MaterialProduce> {
    //select * from table where create_time = max(create_time) and flag = ? group by base_id
    @Select("SELECT * FROM sh_material_produce WHERE flag = ? group by base_id having time_estimate = max(time_estimate)  ")
    List<MaterialProduce> groupByCreatTime(Integer flag);


}

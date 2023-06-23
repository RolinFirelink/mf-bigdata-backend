package com.arg.smart.web.cargo.mapper;

import com.arg.smart.web.cargo.entity.CarrierTransportationVolumeData;
import com.arg.smart.web.cargo.entity.ProductCirculationData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @description: 货运表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
public interface ProductCirculationDataMapper extends BaseMapper<ProductCirculationData> {
    @Select("select flag,company_id,sum(transportation_quantity) as transportation_quantity_tall from sh_product_circulation_data group by flag,company_id")
    List<CarrierTransportationVolumeData> selectAllByFlagOfMeasure();


}

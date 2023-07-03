package com.arg.smart.web.cargo.mapper;

        import com.arg.smart.web.cargo.entity.CarrierTransportationVolumeData;
        import com.arg.smart.web.cargo.entity.ProductCirculationData;
        import com.arg.smart.web.cargo.entity.vo.CirculationTransportationFrequencyData;
        import com.arg.smart.web.cargo.entity.vo.CirculationTransportationFrequencyDataList;
        import com.arg.smart.web.cargo.entity.vo.TransportInformation;
        import com.baomidou.mybatisplus.core.mapper.BaseMapper;
        import org.apache.ibatis.annotations.Param;
        import org.apache.ibatis.annotations.Select;

        import java.time.LocalDate;
        import java.time.LocalDateTime;
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
    @Select("select flag,batch_number,odd_numbers,shipping_unit,extend_field,receiving_time from sh_product_circulation_data WHERE receiving_time >= #{startDateTime}\n" +
            "  AND receiving_time < #{endDataTime}")
    List<CirculationTransportationFrequencyData> selectOneOfCirculationData(@Param("startDateTime") LocalDateTime startDateTime, @Param("endDataTime") LocalDateTime endDataTime);


    @Select("SELECT flag, avg(transportation_price) as averagePrice, sum(transportation_quantity) as transportationQuantityTall, count(*) as TransportationTimes, receiving_time " +
            "FROM sh_product_circulation_data " +
            "WHERE receiving_time >= #{nineDaysAgo} and flag = #{flag} " +
            "GROUP BY DATE(receiving_time)")
    List<CirculationTransportationFrequencyDataList> createCirculationTransportationFrequencyDataList(@Param("flag") Integer flag, @Param("nineDaysAgo") LocalDate nineDaysAgo);

    @Select("select id,manufacturer,delivery_time,receiving_time,receiver_phone,receiving_location,freight_logistics_transfer_information" +
            " from sh_product_circulation_data where flag = #{flag} and DATE_SUB(CURRENT_TIMESTAMP(),INTERVAL 7 DAY) <= date(create_time) order by create_time DESC limit 0,10")
    List<TransportInformation> getTransportInformation(@Param("flag") int flag);
}

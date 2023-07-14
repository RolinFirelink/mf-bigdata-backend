package com.arg.smart.web.cargo.mapper;

import com.arg.smart.web.cargo.entity.CarrierTransportationVolumeData;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarrierTransportationVolumeDataMapper extends BaseMapper<CarrierTransportationVolumeData> {

}

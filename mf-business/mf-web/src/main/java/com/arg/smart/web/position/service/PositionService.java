package com.arg.smart.web.position.service;

import com.arg.smart.web.cargo.entity.CarrierTransportationVolumeData;
import com.arg.smart.web.position.entity.PositionData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;


public interface PositionService extends IService<PositionData> {

    List<PositionData> statisticalDistribution(Integer flag);
}

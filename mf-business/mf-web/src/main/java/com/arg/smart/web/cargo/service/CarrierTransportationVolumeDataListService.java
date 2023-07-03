package com.arg.smart.web.cargo.service;

import com.arg.smart.web.cargo.entity.CarrierTransportationVolumeData;
import com.arg.smart.web.cargo.entity.vo.CarrierTransportationVolumeDataList;
import com.baomidou.mybatisplus.extension.service.IService;

public interface CarrierTransportationVolumeDataListService extends IService<CarrierTransportationVolumeData>{


    void updateData();

    CarrierTransportationVolumeDataList list(Integer flag);
}



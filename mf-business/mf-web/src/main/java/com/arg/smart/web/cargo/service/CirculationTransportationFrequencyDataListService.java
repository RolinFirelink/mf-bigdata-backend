package com.arg.smart.web.cargo.service;

import com.arg.smart.web.cargo.entity.vo.CirculationTransportationFrequencyData;
import com.arg.smart.web.cargo.entity.vo.CirculationTransportationFrequencyDataList;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface CirculationTransportationFrequencyDataListService extends IService<CirculationTransportationFrequencyDataList> {
    List<CirculationTransportationFrequencyDataList> creatCirculationTransportationFrequencyDataList(Integer flag);
}

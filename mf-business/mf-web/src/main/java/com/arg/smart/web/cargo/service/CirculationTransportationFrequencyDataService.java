package com.arg.smart.web.cargo.service;

import com.arg.smart.web.cargo.entity.vo.CirculationTransportationFrequencyData;
import com.arg.smart.web.cargo.entity.vo.CirculationTransportationFrequencyDataList;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;


public interface CirculationTransportationFrequencyDataService extends IService<CirculationTransportationFrequencyData> {

    List<CirculationTransportationFrequencyData> selectOneOfCirculationData(Date receivingDate);


}

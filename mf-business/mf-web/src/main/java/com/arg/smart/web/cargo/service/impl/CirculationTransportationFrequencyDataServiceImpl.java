package com.arg.smart.web.cargo.service.impl;

import com.arg.smart.web.cargo.entity.vo.CirculationTransportationFrequencyData;
import com.arg.smart.web.cargo.entity.vo.CirculationTransportationFrequencyDataList;
import com.arg.smart.web.cargo.mapper.CirculationTransportationFrequencyDataMapper;
import com.arg.smart.web.cargo.service.CirculationTransportationFrequencyDataService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
@Slf4j
@Service
public class CirculationTransportationFrequencyDataServiceImpl extends ServiceImpl<CirculationTransportationFrequencyDataMapper, CirculationTransportationFrequencyData> implements CirculationTransportationFrequencyDataService {


    @Override
    public List<CirculationTransportationFrequencyData> selectOneOfCirculationData(Date receivingDate) {
        LocalDate localDate = receivingDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDateTime startDateTime = LocalDateTime.of(localDate, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(localDate, LocalTime.MAX);

        QueryWrapper<CirculationTransportationFrequencyData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("flag, batch_number, odd_numbers, shipping_unit, extend_field, receiving_time")
                .ge("receiving_time", startDateTime)
                .lt("receiving_time", endDateTime);

        List<CirculationTransportationFrequencyData> circulationTransportationFrequencyDatas = baseMapper.selectList(queryWrapper);
        return circulationTransportationFrequencyDatas;
    }
}

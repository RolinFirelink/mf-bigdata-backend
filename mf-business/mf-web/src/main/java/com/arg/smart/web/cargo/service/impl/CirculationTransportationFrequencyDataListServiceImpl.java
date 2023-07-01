package com.arg.smart.web.cargo.service.impl;

import com.arg.smart.web.cargo.entity.vo.CirculationTransportationFrequencyData;
import com.arg.smart.web.cargo.entity.vo.CirculationTransportationFrequencyDataList;
import com.arg.smart.web.cargo.mapper.CirculationTransportationFrequencyDataListMapper;
import com.arg.smart.web.cargo.service.CirculationTransportationFrequencyDataListService;
import com.arg.smart.web.cargo.service.CirculationTransportationFrequencyDataService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.sql.Date;
import java.util.List;

@Slf4j
@Service
public class CirculationTransportationFrequencyDataListServiceImpl extends ServiceImpl<CirculationTransportationFrequencyDataListMapper, CirculationTransportationFrequencyDataList> implements CirculationTransportationFrequencyDataListService {
    @Resource
    CirculationTransportationFrequencyDataService circulationTransportationFrequencyDataService;

    @Override
    public List<CirculationTransportationFrequencyDataList> creatCirculationTransportationFrequencyDataList(Integer flag) {
        if (flag == null) {
            log.info("查询失败");
            return null;
        }

        QueryWrapper<CirculationTransportationFrequencyDataList> queryWrapper = new QueryWrapper<>();
        //得到近期（9天）的时间
        LocalDate today = LocalDate.now();
        LocalDate nineDaysAgo = today.minus(9, ChronoUnit.DAYS);
        queryWrapper.select("flag, avg(transportation_price) as averagePrice, sum(transportation_quantity) as transportationQuantityTall,count(*) as TransportationTimes,receiving_time")
                .ge("receiving_time", nineDaysAgo)
                .groupBy("DATE(receiving_time)");
        List<CirculationTransportationFrequencyDataList> cirTransportationDataList = baseMapper.selectList(queryWrapper);
        cirTransportationDataList.stream().forEach(data -> {
            data.setMassUnit("吨");
            //根据时间得到当天的订单
            data.setCirculationTransportationFrequencyDataList(circulationTransportationFrequencyDataService.selectOneOfCirculationData(data.getReceivingTime()));
        });
        return cirTransportationDataList;
    }



}

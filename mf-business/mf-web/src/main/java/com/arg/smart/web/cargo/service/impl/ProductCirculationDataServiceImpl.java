package com.arg.smart.web.cargo.service.impl;

import com.arg.smart.web.cargo.entity.ProductCirculationData;
import com.arg.smart.web.cargo.entity.vo.*;
import com.arg.smart.web.cargo.mapper.CarrierTransportationVolumeDataMapper;
import com.arg.smart.web.cargo.mapper.ProductCirculationDataMapper;
import com.arg.smart.web.cargo.req.ReqProductCirculationData;
import com.arg.smart.web.cargo.service.ProductCirculationDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.math.RoundingMode;
import java.time.LocalDate;
import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 货运表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
@Slf4j
@Service
public class ProductCirculationDataServiceImpl extends ServiceImpl<ProductCirculationDataMapper, ProductCirculationData> implements ProductCirculationDataService {
    @Resource
    private ProductCirculationDataMapper productCirculationDataMapper ;

    @Override
    public List<ProductCirculationData> selectOfOrderInformationList(Integer flag) {
        QueryWrapper<ProductCirculationData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("flag,sh_product_circulation_data.company_id,order_id,delivery_time,company_name,mode_transport,product_name")
                .eq("flag",flag);
        return list(queryWrapper);
    }

    /*
     * 根据发货地址，返回收货发货的相关信息
     * */
    @Override
    public List<ProductCirculationData> findOrderInformationList(Integer flag, String shippingLocation) {
        QueryWrapper<ProductCirculationData> queryWrapperOfCarrier = new QueryWrapper<>();
        queryWrapperOfCarrier.select("flag,shipper,shipping_location,shipping_area_code,receiver,receiving_location,receiving_area_code,extend_field")
                .eq("shipping_location",shippingLocation)
                .eq("flag",flag);
        return list(queryWrapperOfCarrier);
    }

    @Override
    public List<ProductCirculationData> selectOfShipmentOrderData(Integer flag) {
        QueryWrapper<ProductCirculationData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("flag,order_id,shipping_location,receiving_location,delivery_time,receiving_time,product_name,shipping_location").
                eq("flag",flag);
        return list(queryWrapper);
    }

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
        List<CirculationTransportationFrequencyDataList> cirTransportationDataList = productCirculationDataMapper.createCirculationTransportationFrequencyDataList(flag,nineDaysAgo);
        if (cirTransportationDataList != null && !cirTransportationDataList.isEmpty()) {
            cirTransportationDataList.forEach(data -> {
                data.setMassUnit("吨");
                //根据时间得到当天的订单
                data.setCirculationTransportationFrequencyDataList(selectOneOfCirculationData(data.getReceivingTime()));
            });
        } else {
            return null;
        }
        return cirTransportationDataList;
    }

    @Override
    public List<CirculationTransportationFrequencyData> selectOneOfCirculationData(Date receivingDate) {
        LocalDate localDate = receivingDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        LocalDateTime startDateTime = LocalDateTime.of(localDate, LocalTime.MIN);
        LocalDateTime endDateTime = LocalDateTime.of(localDate, LocalTime.MAX);

        List<CirculationTransportationFrequencyData> circulationTransportationFrequencyDatas = productCirculationDataMapper.selectOneOfCirculationData(startDateTime,endDateTime);
        if(circulationTransportationFrequencyDatas==null)return null;
        return circulationTransportationFrequencyDatas;
    }
    @Override
    public List<ProductCirculationData> selectListByCondition(ReqProductCirculationData reqProductCirculationData) {
        LambdaQueryWrapper<ProductCirculationData> queryWrapper = new LambdaQueryWrapper<>();
        Long orderId = reqProductCirculationData.getOrderId();
        Integer businessType = reqProductCirculationData.getBusinessType();
        String modeTransport = reqProductCirculationData.getModeTransport();
        if(orderId != null){
            queryWrapper.eq(ProductCirculationData::getOrderId,orderId);
        }
        if(businessType != null){
            queryWrapper.eq(ProductCirculationData::getBusinessType,businessType);
        }
        if(modeTransport != null){
            queryWrapper.like(ProductCirculationData::getModeTransport,modeTransport);
        }
        return list(queryWrapper);
    }


    /**
     * 计算不同订单到各个销售渠道的占比
     * @param flag
     * @return
     */
    @Override
    public Map<String, Double> selectChannelByFlag(int flag) {

        QueryWrapper<ProductCirculationData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("business_type")
                .eq("flag",flag);
        List<ProductCirculationData> dataList = baseMapper.selectList(queryWrapper);
        List<String> p = new ArrayList<>();

        if (dataList != null && !dataList.isEmpty()) {
            p = dataList.stream()
                    .filter(Objects::nonNull)  // 排除为 null 的元素
                    .map(ProductCirculationData::getBusinessType)
                    .collect(Collectors.toList());
        } else {
            return null;
        }

        int total = p.size();
        Map<String, Double> map = new HashMap<>();

        while (!p.isEmpty()) {
            String model = p.get(0);
            List<String> result = p.stream()
                    .filter(model2 -> model2.equals(model))
                    .collect(Collectors.toList());

            double outcome = (double) result.size() / total;
            map.put(model, outcome);
            p.removeAll(result);
            result.clear();
        }

        return map;
    }
    // 根据模块类型字段，统计使用不同运输方式的占比
    @Override
    public Map<String, Double> selectPercentageByFlag(Integer flag) {
        QueryWrapper<ProductCirculationData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("mode_transport")
                    .eq("flag", flag);
        List<ProductCirculationData> productCirculationData = baseMapper.selectList(queryWrapper);
        List<String> p = productCirculationData.stream()
                .map(ProductCirculationData::getModeTransport)
                .collect(Collectors.toList());
        int total = p.size();
        Map<String, Double> map = new HashMap<>();
        while (!p.isEmpty()) {
            String model = p.get(0);
            List<String> result = p.stream()
                    .filter(model2 -> model2.equals(model))
                    .collect(Collectors.toList());
            double outcome = (double) result.size() / total;
            map.put(model, outcome);
            p.removeAll(result);
        }
        return map;
    }
    // 根据模块类型字段，统计运输均价
    @Override
    public BigDecimal selectAverageShippingPriceByFlag(Integer flag) {
        QueryWrapper<ProductCirculationData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("transportation_price")
                .eq("flag",flag);
        List<ProductCirculationData> productCirculationData = baseMapper.selectList(queryWrapper);
        BigDecimal total = new BigDecimal("0");
        total = productCirculationData.stream()
                .map(ProductCirculationData::getTransportationPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal length = new BigDecimal(productCirculationData.size());
        total = total.divide(length, 2, RoundingMode.HALF_UP);
        return total;
    }
        //承运商的数量，通过的到近段时间（如一年 或 一月）计算的不同订单到各渠道的数量
    @Override
    public Map<String, Integer> selectCompanyQuantity(Integer flag) {
        QueryWrapper<ProductCirculationData> queryWrapper = new QueryWrapper<>();
        LocalDate currentDate = LocalDate.now();
        LocalDate oneYearAgo = currentDate.minusYears(1);
        queryWrapper.select("company_name")
                .eq("flag", flag)
                .ge("receiving_time", oneYearAgo);
        List<ProductCirculationData> productCirculationData = baseMapper.selectList(queryWrapper);
        Map<String, Integer> countMap = new HashMap<>();
        productCirculationData.stream()
                .map(ProductCirculationData::getCompanyName)
                .forEach(companyName -> countMap.merge(companyName, 1, Integer::sum));
        return countMap;
    }
    @Override
    public List<LocationLatLon> selectLocationLatLon(Integer flag) {
        List<LocationLatLon> locationLatLons = new ArrayList<>();
        List<TempLocation> locations = baseMapper.selectAllCode(flag);
        for(int i = 0;i<locations.size();i++){
            LocationLatLon locationLatLon = new LocationLatLon();
            locationLatLon.setStartLocation(baseMapper.selectLocationByCompanyId(locations.get(i)));
            locationLatLon.setEndLocation(baseMapper.selectLocationByCityCode(locations.get(i)));
            locationLatLon.setFlag(flag);
            locationLatLons.add(locationLatLon);
        }


        //Map<Long,Long> map = baseMapper.selectList();

        return locationLatLons;
    }


}

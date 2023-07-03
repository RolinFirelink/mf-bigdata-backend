package com.arg.smart.web.cargo.service.impl;

import com.arg.smart.web.cargo.entity.CarrierTransportationVolumeData;

import com.arg.smart.web.cargo.entity.vo.CarrierTransportationVolumeDataList;
import com.arg.smart.web.cargo.mapper.CarrierTransportationVolumeDataMapper;
import com.arg.smart.web.cargo.mapper.ProductCirculationDataMapper;
import com.arg.smart.web.cargo.service.CarrierTransportationVolumeDataListService;
import com.arg.smart.web.company.mapper.CompanyMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class CarrierTransportationVolumeDataListServiceImpl extends ServiceImpl<CarrierTransportationVolumeDataMapper,CarrierTransportationVolumeData> implements CarrierTransportationVolumeDataListService {


    @Resource
    private ProductCirculationDataMapper productCirculationDataMapper;

    @Resource
    private CompanyMapper companyMapper;
    @Override
    public void updateData() {
        // 根据flag、company_id聚合查询出运输总量
        List<CarrierTransportationVolumeData> list = productCirculationDataMapper.selectAllByFlagOfMeasure();
        // 给公司名、统计时间赋值
        List<CarrierTransportationVolumeData> collect = list.stream().peek(item -> {
            item.setCompanyName(companyMapper.getNameById(item.getCompanyId()));
            item.setDateMeasureTatol(new Date());
        }).collect(Collectors.toList());
        // 批量添加
        this.saveBatch(collect);
    }

    @Override
    public CarrierTransportationVolumeDataList list(Integer flag) {
        QueryWrapper<CarrierTransportationVolumeData> queryWrapperOfCarrier = new QueryWrapper<>();
        queryWrapperOfCarrier.select("company_id,company_name,transportation_quantity_tall,MAX(date_measure_tatol) as date_measure_tatol")
                .eq("flag",flag)
                .groupBy("company_name");
        List<CarrierTransportationVolumeData> carrierTransportationVolumeData = baseMapper.selectList(queryWrapperOfCarrier);
        CarrierTransportationVolumeDataList carrierTransportationVolumeDataList = new CarrierTransportationVolumeDataList();
        carrierTransportationVolumeDataList.setCarrierTransportationVolumeDataList(carrierTransportationVolumeData);
        carrierTransportationVolumeDataList.setMassUnit("公斤");
        return carrierTransportationVolumeDataList;
    }
}

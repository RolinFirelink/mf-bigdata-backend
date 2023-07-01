package com.arg.smart.web.cargo.controller;


import com.arg.smart.common.core.web.Result;
import com.arg.smart.web.cargo.entity.vo.CarrierTransportationVolumeDataList;
import com.arg.smart.web.cargo.service.CarrierTransportationVolumeDataListService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @description: 货运统计表
 * @author cgli
 * @date: 2023-05-24
 * @version: V1.0.0
 */
@Slf4j
@Api(tags = "货运统计")
@RestController
@RequestMapping("/cargoData")
public class CarrierTransportationVolumeDataController {

    @Resource
    private CarrierTransportationVolumeDataListService carrierTransportationVolumeDataListService;


    @ApiOperation(value = "产品运输量的总量",notes = "获得到每个运承商的总运货量")
    @GetMapping("/totalTransportationVolume/{flag}")
    public Result<CarrierTransportationVolumeDataList> carrierTotalFreightTable(@ApiParam(name = "flag", value = "大数据类型") @PathVariable Integer flag){
        CarrierTransportationVolumeDataList carrierTransportationVolumeDataList = carrierTransportationVolumeDataListService.list(flag);
        return Result.ok(carrierTransportationVolumeDataList);
    }


}

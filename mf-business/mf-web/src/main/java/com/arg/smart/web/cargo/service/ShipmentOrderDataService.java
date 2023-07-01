package com.arg.smart.web.cargo.service;

import com.arg.smart.web.cargo.entity.vo.ShipmentOrderData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ShipmentOrderDataService extends IService<ShipmentOrderData> {


    List<ShipmentOrderData> selectOfShipmentOrderData(Integer flag);
}

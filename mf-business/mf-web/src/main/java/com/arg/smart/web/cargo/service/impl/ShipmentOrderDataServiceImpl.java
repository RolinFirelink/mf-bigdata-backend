package com.arg.smart.web.cargo.service.impl;


import com.arg.smart.web.cargo.entity.vo.ShipmentOrderData;
import com.arg.smart.web.cargo.mapper.ShipmentOrderDataMapper;
import com.arg.smart.web.cargo.service.ShipmentOrderDataService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ShipmentOrderDataServiceImpl extends ServiceImpl<ShipmentOrderDataMapper, ShipmentOrderData> implements ShipmentOrderDataService {
    @Override
    public List<ShipmentOrderData> selectOfShipmentOrderData(Integer flag) {
        QueryWrapper<ShipmentOrderData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("flag,order_id,shipping_location,receiving_location,delivery_time,receiving_time,product_name,shipping_location").
                eq("flag",flag);
        List<ShipmentOrderData> shipmentOrderDataList = baseMapper.selectList(queryWrapper);
        return shipmentOrderDataList;
    }
}

package com.arg.smart.web.cargo.service.impl;

import com.arg.smart.web.cargo.entity.vo.OrderInformationList;
import com.arg.smart.web.cargo.mapper.OrderInformationListMapper;
import com.arg.smart.web.cargo.service.OrderInformationListService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OrderInformationListServiceImpl extends ServiceImpl<OrderInformationListMapper,OrderInformationList> implements OrderInformationListService {

    @Resource
    private OrderInformationListMapper orderInformationListMapper;

    /*
    * 根据发货地址，返回收货发货的相关信息
    * */
    @Override
    public List<OrderInformationList> findOrderInformationList(Integer flag,String shippingLocation) {
        QueryWrapper<OrderInformationList> queryWrapperOfCarrier = new QueryWrapper<>();
        queryWrapperOfCarrier.select("flag,shipper,shipping_location,shipping_area_code,receiver,receiving_location,receiving_area_code,extend_field")
                .eq("shipping_location",shippingLocation)
                .eq("flag",flag);
        List<OrderInformationList> orderInformationLists = orderInformationListMapper.selectList(queryWrapperOfCarrier);

        return orderInformationLists;
    }
}

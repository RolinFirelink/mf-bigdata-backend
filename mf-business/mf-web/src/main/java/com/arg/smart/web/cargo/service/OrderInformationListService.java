package com.arg.smart.web.cargo.service;

import com.arg.smart.web.cargo.entity.vo.OrderInformationList;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface OrderInformationListService extends IService<OrderInformationList> {

    /*
     * 发货相关的运输订单信息
     * */
    List<OrderInformationList> findOrderInformationList(Integer flag, String shippinglocation);
}

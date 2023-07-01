package com.arg.smart.web.cargo.service;

import com.arg.smart.web.cargo.entity.vo.OrderInformationData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface OrderInformationListService extends IService<OrderInformationData> {
    List<OrderInformationData> selectOfOrderInformationList(Integer flag);
}

package com.arg.smart.web.cargo.service.impl;

import com.arg.smart.web.cargo.entity.vo.OrderInformationData;
import com.arg.smart.web.cargo.mapper.OrderInformationListMapper;
import com.arg.smart.web.cargo.service.OrderInformationListService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OrderInformationListServiceImpl extends ServiceImpl<OrderInformationListMapper, OrderInformationData> implements OrderInformationListService {

    @Override
    public List<OrderInformationData> selectOfOrderInformationList(Integer flag) {
        QueryWrapper<OrderInformationData> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("flag,sh_product_circulation_data.company_id,order_id,delivery_time,company_name,mode_transport,product_name")
                .eq("flag",flag);
        List<OrderInformationData> orderInformationLists = baseMapper.selectList(queryWrapper);
        return orderInformationLists;
    }

}

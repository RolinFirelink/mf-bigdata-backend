package com.arg.smart.web.order.service.impl;


import com.arg.smart.web.order.entity.OrderDetail;
import com.arg.smart.web.order.mapper.OrderDetailMapper;
import com.arg.smart.web.order.req.ReqOrderDetail;
import com.arg.smart.web.order.service.OrderDetailService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author cgli
 * @description: 订单数据明细表
 * @date: 2023-05-22
 * @version: V1.0.0
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Resource
    private OrderDetailMapper detailDao;

    @Override
    public List<OrderDetail> list(ReqOrderDetail reqOrderDetail) {
        if (reqOrderDetail == null) {
            return detailDao.selectList(null);
        }
        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(reqOrderDetail.getProductId() != null, OrderDetail::getProductId, reqOrderDetail.getProductId());
        wrapper.like(reqOrderDetail.getProductName() != null && !" ".equals(reqOrderDetail.getProductName())
                , OrderDetail::getProductName, reqOrderDetail.getProductName());
        return detailDao.selectList(wrapper);
    }
}

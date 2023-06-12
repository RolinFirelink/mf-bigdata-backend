package com.arg.smart.web.order.service.impl;


import com.arg.smart.web.order.entity.OrderDetail;
import com.arg.smart.web.order.mapper.OrderDetailMapper;
import com.arg.smart.web.order.service.OrderDetailService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author cgli
 * @description: 订单数据明细表
 * @date: 2023-05-22
 * @version: V1.0.0
 */
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {

    @Override
    public List<OrderDetail> list(Long orderId) {
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(OrderDetail::getOrderId, orderId);
        return this.list(queryWrapper);
    }

    private LocalDateTime getStartTime(Integer time, Integer timeFlag) {
        LocalDateTime startTime = null;
        LocalDateTime now = LocalDateTime.now();
        //根据年月日分类设定起始时间
        if (timeFlag == 0) {
            startTime = now.minusYears(time);
        } else if (timeFlag == 1) {
            startTime = now.minusMonths(time);
        } else if (timeFlag == 2) {
            startTime = now.minusWeeks(time);
        } else if (timeFlag == 3) {
            startTime = now.minusDays(time);
        }
        return startTime;
    }

    @Override
    public List<String> averageSales(Integer time, Integer timeFlag) {
        LocalDateTime startTime = getStartTime(time, timeFlag);
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.orderByAsc(OrderDetail::getFlag)
                .orderByDesc(OrderDetail::getSalesQuantity)
                .between(OrderDetail::getCreateTime, startTime, LocalDateTime.now());
        List<OrderDetail> queryResult = this.list(queryWrapper);
        List<String> list = new ArrayList<>();
        if (queryResult.size() == 0) {
            return list;
        }
        Long averageSale = 0L;
        Integer flag = queryResult.get(0).getFlag();
        Long size = 0L;
        for (OrderDetail item : queryResult) {
            if (item.getFlag() == flag) {
                averageSale += item.getSalesQuantity();
                size++;
            } else if (item.getFlag() != flag) {
                list.add(BigDecimal.valueOf(averageSale).divide(BigDecimal.valueOf(size)).setScale(2, RoundingMode.HALF_UP).toString());
                averageSale = item.getSalesQuantity();
                flag = item.getFlag();
                size = 1L;
            }
        }
        if (queryResult.size() > 1
                && queryResult.get(queryResult.size() - 1).getFlag() != queryResult.get(queryResult.size() - 2).getFlag()) {
            list.add(BigDecimal.valueOf(queryResult.get(queryResult.size() - 1).getSalesQuantity()).toString());
        }
        return list;
    }

    @Override
    public List<String> averageSales(String startTime, String endTime, Integer flag) {
        LambdaQueryWrapper<OrderDetail> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(OrderDetail::getFlag, flag)
                .orderByAsc(OrderDetail::getMaterialName)
                .between(OrderDetail::getCreateTime, startTime, endTime);
        List<OrderDetail> queryResult = this.list(queryWrapper);
        ArrayList<String> list = new ArrayList<>();
        if (queryResult.size() == 0) {
            return list;
        }
        Long averageSale = 0L;
        Long size = 0L;
        String materialName = queryResult.get(0).getMaterialName();
        for (OrderDetail item : queryResult) {
            if (item.getMaterialName() == materialName) {
                averageSale += item.getSalesQuantity();
                size++;
            } else if (item.getMaterialName() != materialName) {
                list.add(BigDecimal.valueOf(averageSale).divide(BigDecimal.valueOf(size)).setScale(2, RoundingMode.HALF_UP).toString());
                averageSale = item.getSalesQuantity();
                materialName = item.getMaterialName();
                size = 1L;
            }
        }
        if (queryResult.size() > 1
                && queryResult.get(queryResult.size() - 1).getMaterialName().equals(queryResult.get(queryResult.size() - 2).getMaterialName())) {
            list.add(BigDecimal.valueOf(queryResult.get(queryResult.size() - 1).getSalesQuantity()).toString());
        }
        return list;
    }
}

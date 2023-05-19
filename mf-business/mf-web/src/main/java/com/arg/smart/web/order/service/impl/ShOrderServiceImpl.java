package com.arg.smart.web.order.service.impl;

import com.arg.smart.web.order.entity.ShOrder;
import com.arg.smart.web.order.mapper.ShOrderMapper;
import com.arg.smart.web.order.service.ShOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @description: 订单数据主表
 * @author cgli
 * @date: 2023-05-19
 * @version: V1.0.0
 */
@Service
public class ShOrderServiceImpl extends ServiceImpl<ShOrderMapper, ShOrder> implements ShOrderService {

}

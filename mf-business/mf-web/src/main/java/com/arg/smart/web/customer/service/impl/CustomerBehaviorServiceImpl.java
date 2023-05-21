package com.arg.smart.web.customer.service.impl;

import com.arg.smart.web.customer.entity.CustomerBehavior;
import com.arg.smart.web.customer.mapper.CustomerBehaviorMapper;
import com.arg.smart.web.customer.service.CustomerBehaviorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @description: 客户消费行为表
 * @author cgli
 * @date: 2023-05-17
 * @version: V1.0.0
 */
@Service
public class CustomerBehaviorServiceImpl extends ServiceImpl<CustomerBehaviorMapper, CustomerBehavior> implements CustomerBehaviorService {

    @Override
    public boolean saveBehavior(CustomerBehavior customerBehavior) {
        return save(customerBehavior);
    }
}

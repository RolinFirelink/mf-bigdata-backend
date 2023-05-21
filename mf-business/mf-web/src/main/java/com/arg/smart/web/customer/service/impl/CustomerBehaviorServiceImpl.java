package com.arg.smart.web.customer.service.impl;

import com.arg.smart.web.customer.entity.Customer;
import com.arg.smart.web.customer.entity.CustomerBehavior;
import com.arg.smart.web.customer.mapper.CustomerBehaviorMapper;
import com.arg.smart.web.customer.service.CustomerBehaviorService;
import com.arg.smart.web.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @author cgli
 * @description: 客户消费行为表
 * @date: 2023-05-17
 * @version: V1.0.0
 */
@Service
public class CustomerBehaviorServiceImpl extends ServiceImpl<CustomerBehaviorMapper, CustomerBehavior> implements CustomerBehaviorService {

    @Autowired
    @Lazy
    CustomerService customerService;

    @Override
    public boolean saveBehavior(CustomerBehavior customerBehavior) {
        Customer customer = customerService.getById(customerBehavior.getConsumerId());
        if (customer == null) {
            return false;
        }
        return save(customerBehavior);
    }
}

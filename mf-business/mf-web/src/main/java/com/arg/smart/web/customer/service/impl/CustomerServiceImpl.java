package com.arg.smart.web.customer.service.impl;

import com.arg.smart.web.customer.entity.Customer;
import com.arg.smart.web.customer.mapper.CustomerMapper;
import com.arg.smart.web.customer.service.CustomerService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * @description: 客户表
 * @author cgli
 * @date: 2023-05-25
 * @version: V1.0.0
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {

}

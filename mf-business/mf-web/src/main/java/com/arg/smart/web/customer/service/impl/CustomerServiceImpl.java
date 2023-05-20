package com.arg.smart.web.customer.service.impl;

import com.arg.smart.web.customer.entity.Customer;
import com.arg.smart.web.customer.mapper.CustomerMapper;
import com.arg.smart.web.customer.service.CustomerService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * @description: 客户表
 * @author cgli
 * @date: 2023-05-17
 * @version: V1.0.0
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {


    @Override
    public List<Customer> listByIdOrName(String idOrName) {
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(Customer::getId, idOrName)
                .or()
                .like(Customer::getName, idOrName);

        List<Customer> customers = list(queryWrapper);

//        customers.sort(Comparator.comparingInt(c -> {
//            String id = String.valueOf(c.getId());
//            String name = c.getName();
//            int index1 = id.indexOf(idOrName);
//            int index2 = name.indexOf(idOrName);
//            return (index1 >= 0 ? index1 : Integer.MAX_VALUE) +
//                    (index2 >= 0 ? index2 : Integer.MAX_VALUE);
//        }));

        return list(queryWrapper);
    }
}

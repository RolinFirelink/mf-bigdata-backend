package com.arg.smart.web.customer.service;

import com.arg.smart.web.customer.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 客户表
 * @author cgli
 * @date: 2023-05-17
 * @version: V1.0.0
 */
public interface CustomerService extends IService<Customer> {

    List<Customer> listByIdOrName(String idOrName);

    boolean delCustomer(String id);

    //批量
    boolean delCustomer(List<String> ids);
}

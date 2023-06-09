package com.arg.smart.web.customer.service;

import com.arg.smart.web.customer.entity.Customer;
import com.arg.smart.web.customer.req.ReqCustomer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * @description: 客户表
 * @author cgli
 * @date: 2023-05-25
 * @version: V1.0.0
 */
public interface CustomerService extends IService<Customer> {

    List<Customer> selectListByCondition(ReqCustomer reqCustomer);

    // 根据年龄段和flag统计用户数量
    Map<String, Long> countByAgeRangeAndFlag(Integer flag);

    // 根据flag字段统计所有职业的用户数量
    Map<String, Long> countByOccupationAndFlag(Integer flag);
}

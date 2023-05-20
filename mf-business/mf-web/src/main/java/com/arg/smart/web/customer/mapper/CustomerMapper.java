package com.arg.smart.web.customer.mapper;

import com.arg.smart.web.customer.entity.Customer;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @description: 客户表
 * @author cgli
 * @date: 2023-05-17
 * @version: V1.0.0
 */
@Mapper
public interface CustomerMapper extends BaseMapper<Customer> {

}

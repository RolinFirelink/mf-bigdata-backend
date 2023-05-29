package com.arg.smart.web.customer.mapper;

import com.arg.smart.web.customer.entity.Customer;
import com.arg.smart.web.customer.entity.counter.OccupationCounter;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @description: 客户表
 * @author cgli
 * @date: 2023-05-25
 * @version: V1.0.0
 */
public interface CustomerMapper extends BaseMapper<Customer> {

    @Select("SELECT occupation, COUNT(*) AS cnt FROM sh_customer WHERE flag =  #{flag} GROUP BY occupation")
    List<OccupationCounter> countByOccupation(Integer flag);

}

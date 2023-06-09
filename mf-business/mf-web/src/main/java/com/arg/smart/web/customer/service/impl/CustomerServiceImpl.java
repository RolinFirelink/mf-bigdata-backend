package com.arg.smart.web.customer.service.impl;

import com.arg.smart.web.customer.entity.Customer;
import com.arg.smart.web.customer.entity.counter.OccupationCounter;
import com.arg.smart.web.customer.mapper.CustomerMapper;
import com.arg.smart.web.customer.req.ReqCustomer;
import com.arg.smart.web.customer.service.CustomerService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description: 客户表
 * @author cgli
 * @date: 2023-05-25
 * @version: V1.0.0
 */
@Service
@Slf4j
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {


    @Override
    public List<Customer> selectListByCondition(ReqCustomer reqCustomer) {
        LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
        String name = reqCustomer.getName();
        Integer gender = reqCustomer.getGender();

        if(name != null){
            queryWrapper.like(Customer::getName,name);
        }
        if(gender != null){
            queryWrapper.eq(Customer::getGender,gender);
        }

        return this.list(queryWrapper);
    }

    // 根据年龄段和flag统计用户数量
    @Override
    public Map<String, Long> countByAgeRangeAndFlag(Integer flag) {
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.eq("flag", flag);
        long count0_19 = baseMapper.selectCount(wrapper.between("age", 0, 19));
        long count20_39 = baseMapper.selectCount(wrapper.between("age", 20, 39));
        long count40_59 = baseMapper.selectCount(wrapper.between("age", 40, 59));
        long count60_79 = baseMapper.selectCount(wrapper.between("age", 60, 79));
        long count80 = baseMapper.selectCount(wrapper.ge("age", 80));
        Map<String, Long> res = new HashMap<>();
        res.put("0-19", count0_19);
        res.put("20-39", count20_39);
        res.put("40-59", count40_59);
        res.put("60-79", count60_79);
        res.put(">=80", count80);
        return res;
    }

    // 根据flag字段统计所有职业的用户数量
    @Override
    public Map<String, Long> countByOccupationAndFlag(Integer flag) {
        List<OccupationCounter> counters = baseMapper.countByOccupation(flag);
        Map<String, Long> res = new HashMap<>();
        for(OccupationCounter counter : counters){

            if(counter.getCnt() != null){
                String occupation = counter.getOccupation();
                if(occupation == null) occupation = "未知";
                res.put(occupation,counter.getCnt());
            }
        }
        return res;
    }



}

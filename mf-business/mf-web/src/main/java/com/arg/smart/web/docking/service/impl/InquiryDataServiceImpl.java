package com.arg.smart.web.docking.service.impl;

import com.arg.smart.web.docking.entity.InquiryData;
import com.arg.smart.web.docking.mapper.InquiryDataMapper;
import com.arg.smart.web.docking.req.ReqInquiryData;
import com.arg.smart.web.docking.service.InquiryDataService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @description: 惠农网省份月采购数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Service
public class InquiryDataServiceImpl extends ServiceImpl<InquiryDataMapper, InquiryData> implements InquiryDataService {

    @Override
    public List<InquiryData> list(ReqInquiryData reqInquiryData) {
        LambdaQueryWrapper<InquiryData> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer flag = reqInquiryData.getFlag();
        String province = reqInquiryData.getProvince();
        String startMonth = reqInquiryData.getStartMonth();
        String endMonth = reqInquiryData.getEndMonth();
        lambdaQueryWrapper.eq(flag != null,InquiryData::getFlag,flag);
        lambdaQueryWrapper.like(province != null,InquiryData::getProvinceName,province);
        lambdaQueryWrapper.ge(startMonth != null,InquiryData::getDate,startMonth);
        lambdaQueryWrapper.le(endMonth != null,InquiryData::getDate,endMonth);
        return this.list(lambdaQueryWrapper);
    }
}

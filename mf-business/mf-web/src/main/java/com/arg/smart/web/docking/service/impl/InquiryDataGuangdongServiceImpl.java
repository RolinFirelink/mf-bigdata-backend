package com.arg.smart.web.docking.service.impl;

import com.arg.smart.web.docking.controller.InquiryDataGuangdongController;
import com.arg.smart.web.docking.entity.InquiryData;
import com.arg.smart.web.docking.entity.InquiryDataGuangdong;
import com.arg.smart.web.docking.mapper.InquiryDataGuangdongMapper;
import com.arg.smart.web.docking.req.ReqInquiryDataGuangdong;
import com.arg.smart.web.docking.service.InquiryDataGuangdongService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

/**
 * @description: 惠农网广东月供应数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
@Service
public class InquiryDataGuangdongServiceImpl extends ServiceImpl<InquiryDataGuangdongMapper, InquiryDataGuangdong> implements InquiryDataGuangdongService {

    @Override
    public List<InquiryDataGuangdong> list(ReqInquiryDataGuangdong reqInquiryDataGuangdong) {

        LambdaQueryWrapper<InquiryDataGuangdong> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        Integer flag = reqInquiryDataGuangdong.getFlag();
        String startMonth = reqInquiryDataGuangdong.getStartMonth();
        String endMonth = reqInquiryDataGuangdong.getEndMonth();
        lambdaQueryWrapper.eq(flag != null,InquiryDataGuangdong::getFlag,flag);
        lambdaQueryWrapper.ge(startMonth != null,InquiryDataGuangdong::getDate,startMonth);
        lambdaQueryWrapper.le(endMonth != null,InquiryDataGuangdong::getDate,endMonth);
        return this.list(lambdaQueryWrapper);
    }
}

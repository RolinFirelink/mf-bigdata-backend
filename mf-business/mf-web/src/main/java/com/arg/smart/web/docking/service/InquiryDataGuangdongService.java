package com.arg.smart.web.docking.service;


import com.arg.smart.web.docking.entity.InquiryDataGuangdong;
import com.arg.smart.web.docking.req.ReqInquiryDataGuangdong;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 惠农网广东月供应数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
public interface InquiryDataGuangdongService extends IService<InquiryDataGuangdong> {

    List<InquiryDataGuangdong> list(ReqInquiryDataGuangdong reqInquiryDataGuangdong);
}

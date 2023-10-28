package com.arg.smart.web.docking.service;


import com.arg.smart.web.docking.entity.InquiryData;
import com.arg.smart.web.docking.req.ReqInquiryData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 惠农网省份月采购数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
public interface InquiryDataService extends IService<InquiryData> {

    List<InquiryData> list(ReqInquiryData reqInquiryData);
}

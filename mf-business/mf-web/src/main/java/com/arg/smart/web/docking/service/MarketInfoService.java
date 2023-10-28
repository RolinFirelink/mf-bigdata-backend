package com.arg.smart.web.docking.service;

import com.arg.smart.web.docking.entity.MarketInfo;
import com.arg.smart.web.docking.req.ReqMarketInfo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 惠农网产品信息表
 * @author cgli
 * @date: 2023-09-26
 * @version: V1.0.0
 */
public interface MarketInfoService extends IService<MarketInfo> {

    List<MarketInfo> list(ReqMarketInfo reqMarketInfo);
}

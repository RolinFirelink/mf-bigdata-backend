package com.arg.smart.web.docking.service;

import com.arg.smart.web.docking.entity.SellDataGuangdong;
import com.arg.smart.web.docking.req.ReqSellDataGuangdong;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 惠农网广东月销售数据
 * @author cgli
 * @date: 2023-09-15
 * @version: V1.0.0
 */
public interface SellDataGuangdongService extends IService<SellDataGuangdong> {

    List<SellDataGuangdong> list(ReqSellDataGuangdong reqSellDataGuangdong);
}

package com.arg.smart.web.statistics.service;


import com.arg.smart.web.statistics.entity.MarketConcentration;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 市场集中度
 * @author cgli
 * @date: 2023-08-24
 * @version: V1.0.0
 */
public interface MarketConcentrationService extends IService<MarketConcentration> {

    void updateData();
}

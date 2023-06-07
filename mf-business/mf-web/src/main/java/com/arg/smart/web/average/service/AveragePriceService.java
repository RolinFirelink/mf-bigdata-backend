package com.arg.smart.web.average.service;

import com.arg.smart.web.average.entity.AveragePrice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 均价表
 * @author cgli
 * @date: 2023-06-06
 * @version: V1.0.0
 */
public interface AveragePriceService extends IService<AveragePrice> {

    /**
     * 记录均价
     */
    boolean timingSave();
}

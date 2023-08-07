package com.arg.smart.web.product.service;

import com.arg.smart.web.product.entity.ProductMarketNums;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @description: 批发市场采购量表
 * @author cgli
 * @date: 2023-07-30
 * @version: V1.0.0
 */
public interface ProductMarketNumsService extends IService<ProductMarketNums> {

    /**
     * 每日定时从惠农网采购大厅中爬取数据
     *
     */
//    @Scheduled(cron = "0 31 * * * ?") // 1点半
    void purchaseScheduledSave();

    /**
     * 从惠农网采购大厅中爬取数据
     * @return
     */
    boolean saveByPurchase();
}

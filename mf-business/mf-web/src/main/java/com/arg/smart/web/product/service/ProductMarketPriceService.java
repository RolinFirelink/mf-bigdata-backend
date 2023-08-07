package com.arg.smart.web.product.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.product.entity.ProductMarketPrice;
import com.arg.smart.web.product.req.ReqProductMarketPrice;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * @description: 产品批发价格表
 * @author cgli
 * @date: 2023-07-08
 * @version: V1.0.0
 */
public interface ProductMarketPriceService extends IService<ProductMarketPrice> {

    /**
     * 每日定时爬取农情网数据到数据库中
     *
     */
//    @Scheduled(cron = "0 0 0 * * ?") // 每天0点触发
    void nongQingScheduledSave();

    /**
     * 通过爬虫将农情站中的数据添加到数据库中
     *
     */
    boolean nongQingSave();

    /**
     * 每日定时爬取食品商务网数据到数据库中
     *
     */
//    @Scheduled(cron = "0 30 * * * ?") // 0点半
    void foodScheduledSave();

    /**
     * 通过爬虫将食品商务网中的数据添加到数据库中
     * @return
     */
    boolean foodSave();

    /**
     * 每日定时爬取农产品商务网数据到数据库中
     *
     */
//    @Scheduled(cron = "0 1 * * * ?") // 1点
    void mofcomScheduledSave();

    /**
     * 通过爬虫将农产品商务信息添加到数据库中
     * @return
     */
    boolean mofcomSave();

    /**
     * 后台分页查询
     * @param reqPage
     * @return
     */
    PageResult<ProductMarketPrice> list(ReqProductMarketPrice reqPage);
}

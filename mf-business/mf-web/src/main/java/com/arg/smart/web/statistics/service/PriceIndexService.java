package com.arg.smart.web.statistics.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.statistics.entity.PriceIndex;
import com.arg.smart.web.statistics.req.ReqBuyersIndex;
import com.arg.smart.web.statistics.req.ReqPriceIndex;
import com.arg.smart.web.statistics.vo.BuyersIndexVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 采购商价格指数
 * @author cgli
 * @date: 2023-08-09
 * @version: V1.0.0
 */
public interface PriceIndexService extends IService<PriceIndex> {

    /**
     * 查询采购商价格指数
     * @param reqPriceIndex 采购商价格指数条件查询类
     * @return List<PriceIndex>
     */
    List<PriceIndex> list(ReqPriceIndex reqPriceIndex);

    /**
     * 分页查询采购商价格指数
     * @param reqPriceIndex 采购商价格指数条件查询类
     * @return List<PriceIndex>
     */
    PageResult<PriceIndex> listPage(ReqPriceIndex reqPriceIndex);

    /**
     * 更新所有采购商价格指数，更新一次
     */
    void updatePriceIndex();

    /**
     * 更新某年某月所有采购商价格指数，每月更新
     * @param year 年份
     * @param month 月份
     */
    void updatePriceIndex(int year, int month);

    /**
     * 查询采购商指数
     * @param reqBuyersIndex 采购商指数条件查询类
     * @return BuyersIndexVO
     */
    BuyersIndexVO list(ReqBuyersIndex reqBuyersIndex);
}

package com.arg.smart.web.statistics.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.statistics.entity.PriceIndex;
import com.arg.smart.web.statistics.req.ReqPriceIndex;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 采购商价格指数
 * @author cgli
 * @date: 2023-08-09
 * @version: V1.0.0
 */
public interface PriceIndexService extends IService<PriceIndex> {

    List<PriceIndex> list(ReqPriceIndex reqPriceIndex);


    PageResult<PriceIndex> listPage(ReqPriceIndex reqPriceIndex);

    void updatePriceIndex();

    List<PriceIndex> publicAvg(ReqPriceIndex reqPriceIndex);

}

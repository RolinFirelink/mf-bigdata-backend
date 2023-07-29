package com.arg.smart.web.statistics.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.statistics.entity.BuyersIndex;
import com.arg.smart.web.statistics.req.ReqBuyersIndex;
import com.arg.smart.web.statistics.vo.BuyersIndexVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 采购商指数
 * @author cgli
 * @date: 2023-07-16
 * @version: V1.0.0
 */
public interface BuyersIndexService extends IService<BuyersIndex> {

    List<BuyersIndex> list(ReqBuyersIndex reqBuyersIndex);

    PageResult<BuyersIndex> listPage(ReqBuyersIndex reqBuyersIndex);

    List<BuyersIndexVO> getBuyersIndex(ReqBuyersIndex reqBuyersIndex);
}

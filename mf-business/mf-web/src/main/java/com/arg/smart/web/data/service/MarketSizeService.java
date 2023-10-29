package com.arg.smart.web.data.service;

import com.arg.smart.web.data.entity.MarketSize;
import com.arg.smart.web.data.req.ReqMarketSize;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MarketSizeService extends IService<MarketSize> {
    List<MarketSize> list(ReqMarketSize reqMarketSize);
}


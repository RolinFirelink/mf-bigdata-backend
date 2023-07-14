package com.arg.smart.web.company.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.company.entity.OriginPrice;
import com.arg.smart.web.company.req.ReqOriginPrice;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 产地价格
 * @author cgli
 * @date: 2023-07-10
 * @version: V1.0.0
 */
public interface OriginPriceService extends IService<OriginPrice> {

    PageResult<OriginPrice> listPage(ReqOriginPrice reqOriginPrice);
}

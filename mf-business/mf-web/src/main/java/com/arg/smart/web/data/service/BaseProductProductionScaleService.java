package com.arg.smart.web.data.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.data.entity.BaseProductProductionScale;
import com.arg.smart.web.data.req.ReqBaseProductProductionScale;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * @description: 基地产品生产规模数据表
 * @author cgli
 * @date: 2023-07-20
 * @version: V1.0.0
 */
public interface BaseProductProductionScaleService extends IService<BaseProductProductionScale> {

    PageResult<BaseProductProductionScale> list(ReqBaseProductProductionScale reqBaseProductProductionScale);
}

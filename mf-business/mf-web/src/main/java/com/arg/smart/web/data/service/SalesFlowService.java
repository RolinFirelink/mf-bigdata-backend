package com.arg.smart.web.data.service;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.data.entity.SalesFlow;
import com.arg.smart.web.data.req.ReqSalesFlow;
import com.arg.smart.web.position.entity.PositionData;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @description: 销售流向
 * @author cgli
 * @date: 2023-07-18
 * @version: V1.0.0
 */
public interface SalesFlowService extends IService<SalesFlow> {

    PageResult<SalesFlow> pageList(ReqSalesFlow reqSalesFlow);

    List<SalesFlow> list(ReqSalesFlow reqSalesFlow);

    boolean saveFlow(SalesFlow salesFlow);

    boolean updateFlowById(SalesFlow salesFlow);
}

package com.arg.smart.web.data.service.impl;

import com.arg.smart.common.core.web.PageResult;
import com.arg.smart.web.data.entity.SalesFlow;
import com.arg.smart.web.data.mapper.SalesFlowMapper;
import com.arg.smart.web.data.req.ReqSalesFlow;
import com.arg.smart.web.data.service.SalesFlowService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author cgli
 * @description: 销售流向
 * @date: 2023-07-18
 * @version: V1.0.0
 */
@Service
public class SalesFlowServiceImpl extends ServiceImpl<SalesFlowMapper, SalesFlow> implements SalesFlowService {

    @Override
    public PageResult<SalesFlow> list(ReqSalesFlow reqSalesFlow) {
        LambdaQueryWrapper<SalesFlow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(reqSalesFlow.getFlag() != null, SalesFlow::getFlag, reqSalesFlow.getFlag());
        return new PageResult<>(this.list(queryWrapper));
    }
}

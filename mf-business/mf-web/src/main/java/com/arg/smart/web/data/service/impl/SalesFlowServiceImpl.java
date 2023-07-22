package com.arg.smart.web.data.service.impl;

import com.arg.smart.web.data.entity.SalesFlow;
import com.arg.smart.web.data.mapper.SalesFlowMapper;
import com.arg.smart.web.data.req.ReqSalesFlow;
import com.arg.smart.web.data.service.SalesFlowService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 销售流向
 * @author cgli
 * @date: 2023-07-18
 * @version: V1.0.0
 */
@Service
public class SalesFlowServiceImpl extends ServiceImpl<SalesFlowMapper, SalesFlow> implements SalesFlowService {

    @Override
    public List<SalesFlow> list(ReqSalesFlow reqSalesFlow) {

        Integer flag = reqSalesFlow.getFlag();
        LambdaQueryWrapper<SalesFlow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SalesFlow::getFlag,flag);
        return this.list(queryWrapper);
    }
}
